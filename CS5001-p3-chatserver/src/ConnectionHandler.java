import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * ConnectionHandler
 * A class which handles all the incoming connections to the server.
 * It inherits an interface of Runnable class.
 *
 * @author: Student ID: 220015821
 */
public class ConnectionHandler implements Runnable {

    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private String nickName;
    private String userName;
    private String realName;
    private boolean registered;

    /**
     * Constructor of the class ConnectioHandler.
     *
     * @param client which is the client that would like to join the server.
     */
    public ConnectionHandler(Socket client) {
        this.client = client;
        this.nickName = null;
        this.userName = null;
        this.realName = null;
        this.registered = false;
    }

    /**
     * Overrides the run method of  the Runnable class.
     * sets the incoming message functionality.
     * sets the outgoing message functionality.
     * <p>
     * Continuously listens to the client for an input Command to the server.
     */
    @Override
    public void run() {
        try {
            this.out = new PrintWriter(client.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            String message;
            String command;

            while ((message = this.in.readLine()) != null) { // continuously gets the input from the client
                command = message.split(" ")[0];

                switch (command) { // client commands switch case
                    case "NICK": // Command to set nickname
                        final int nickMessageLimit = 2;
                        String[] nickMessageSplit = message.split(" ", nickMessageLimit);
                        if (nickMessageSplit.length == 2 && nickMessageSplit[1].matches("^[a-zA-Z_][A-Za-z0-9_]{1,9}")) {
                            this.nickName = nickMessageSplit[1];
                        } else {
                            sendMessage(":" + IrcServerMain.getServerName() + " 400 * :Invalid nickname");
                        }
                        break;

                    case "USER": // Command to set username, realname, and register the client
                        if (this.nickName != null) {
                            final int userMessageLimit = 5;
                            final int userNameIndex = 1;
                            final int realNameIndex = 4;
                            String[] userMessageSplit = message.split(" ", userMessageLimit);
                            if (userMessageSplit.length == userMessageLimit && this.userName == null) {
                                if (userMessageSplit[realNameIndex].matches("^:[A-Za-z ]*")) { // checks whether the 4th index starts with ":" or not
                                    this.userName = userMessageSplit[userNameIndex];
                                    this.realName = userMessageSplit[realNameIndex].replaceAll(":", "");
                                    this.registered = true;
                                    sendMessage(":" + IrcServerMain.getServerName() + " 001 " + this.nickName + " :Welcome to the IRC network, " + this.nickName);
                                } else {
                                    sendMessage(":" + IrcServerMain.getServerName() + " 400 * :Invalid arguments to USER command");
                                }
                            } else if (this.userName != null) {
                                sendMessage(":" + IrcServerMain.getServerName() + " 400 * :You are already registered");
                            } else if (userMessageSplit.length < userMessageLimit) {
                                sendMessage(":" + IrcServerMain.getServerName() + " 400 * :Not enough arguments");
                            }
                        } else {
                            sendMessage("You've yet to initialise a nick name!");
                        }
                        break;

                    case "QUIT": // command to quit from the server
                        if (this.registered) {
                            ArrayList<ChannelHandler> removeClientFromChannels = new ArrayList<>();
                            IrcServerMain.broadCast(":" + this.nickName + " QUIT");

                            for (ChannelHandler ch : IrcServerMain.getChannels()) { //removes the client fom all the channels
                                if (ch.getConnection().client.equals(this.client)) {
                                    removeClientFromChannels.add(ch);
                                }
                            }
                            IrcServerMain.getChannels().removeAll(removeClientFromChannels);
                            shutdown();
                        }
                        break;

                    case "JOIN": // command to join a channel in the server or create a new one if none exists
                        final int joinMessageLimit = 2;
                        String[] joinMessageSplit = message.split(" ", joinMessageLimit);
                        if (joinMessageSplit[1].matches("^#[A-Za-z0-9_]*") && this.registered) {
                            ChannelHandler channel = new ChannelHandler(joinMessageSplit[1], this);
                            channel.addClientToChannel();
                            IrcServerMain.broadCastToChannels(":" + this.nickName + " JOIN " + channel.getChannelName(), channel.getChannelName());
                        } else if (!this.registered) {
                            sendMessage(":" + IrcServerMain.getServerName() + " 400 * :You need to register first");
                        } else {
                            sendMessage(":" + IrcServerMain.getServerName() + " 400 * :Invalid channel name");
                        }
                        break;

                    case "PART": // exit from a channel in the server
                        boolean channelExistsForPart = false;
                        final int partMessageLimit = 2;
                        String[] partMessageSplit = message.split(" ", partMessageLimit);
                        if (!this.registered) {
                            sendMessage(":" + IrcServerMain.getServerName() + " 400 * :You need to register first");
                        } else {
                            ArrayList<ChannelHandler> removeClientFromChannel = new ArrayList<>();
                            for (ChannelHandler ch : IrcServerMain.getChannels()) {
                                if (ch.getChannelName().equals(partMessageSplit[1]) && this.registered && (ch.getConnection().client == this.client)) {
                                    channelExistsForPart = true;
                                    IrcServerMain.broadCastToChannels(":" + this.nickName + " PART " + ch.getChannelName(), ch.getChannelName());
                                    removeClientFromChannel.add(ch);
                                }
                            }
                            if (!channelExistsForPart) {
                                sendMessage(":" + IrcServerMain.getServerName() + " 400 " + this.nickName + " :No channel exists with that name");
                            } else {
                                IrcServerMain.getChannels().removeAll(removeClientFromChannel);
                            }
                        }
                        break;

                    case "PRIVMSG": // private message to another client or to a channel
                        final int privateMessageLimit = 3;
                        String[] privateMessageSplit = message.split(" ", privateMessageLimit);
                        String target = privateMessageSplit[1];
                        String privateSendMessage = privateMessageSplit[2].replaceAll(":", "");

                        if (target.matches("^#[A-Za-z0-9_]*") && this.registered && privateMessageSplit.length == privateMessageLimit) {
                            boolean channelExistsForPrivate = false;
                            for (ChannelHandler ch : IrcServerMain.getChannels()) { //checks whether channel is present in the server or not
                                if (ch.getChannelName().equals(target)) {
                                    channelExistsForPrivate = true;
                                    break;
                                }
                            }
                            if (channelExistsForPrivate) {
                                IrcServerMain.broadCastToChannels(":" + this.nickName + " PRIVMSG " + target + " :" + privateSendMessage, target);
                            } else {
                                sendMessage(":" + IrcServerMain.getServerName() + " 400 * :No channel exists with that name");
                            }
                        } else if (this.registered) {
                            boolean nickNameExists = false;
                            for (ConnectionHandler ch : IrcServerMain.getConnections()) { // checks for matching nicknme from the list of clients
                                if (ch.nickName.equals(target) && ch.registered) {
                                    nickNameExists = true;
                                    ch.out.println(":" + this.nickName + " PRIVMSG " + target + " :" + privateSendMessage);
                                }
                            }
                            if (!nickNameExists) {
                                sendMessage(":" + IrcServerMain.getServerName() + " 400 * :No user exists with that name");
                            }
                        } else if (!this.registered) {
                            sendMessage(":" + IrcServerMain.getServerName() + " 400 * :You need to register first");
                        } else if (privateMessageSplit.length < privateMessageLimit) {
                            sendMessage(":" + IrcServerMain.getServerName() + " 400 * :Invalid arguments to PRIVMSG command");
                        }
                        break;

                    case "NAMES": // command to get the nicknames of all users in a given channel
                        if (this.registered) {
                            final int namesMessageLimit = 2;
                            String[] namesMessageSplit = message.split(" ", namesMessageLimit);
                            String channelName = namesMessageSplit[1];
                            boolean channelExistsForNames = false;
                            ArrayList<String> channelNames = new ArrayList<>();

                            if (channelName.matches("^#[A-Za-z0-9_]*")) {
                                for (ChannelHandler ch : IrcServerMain.getChannels()) {
                                    // checks whether the channel name exists or not and adds the clients that exist in that channel to a list
                                    if (ch.getChannelName().equals(channelName)) {
                                        channelExistsForNames = true;
                                        channelNames.add(ch.getConnection().nickName);
                                    }
                                }
                            } else {
                                sendMessage("A channel name must be a single # symbol followed by any number of letters, numbers and underscores");
                            }

                            if (!channelExistsForNames) {
                                sendMessage(":" + IrcServerMain.getServerName() + " 400 * :No channel exists with that name");
                            } else {
                                this.out.print(":" + IrcServerMain.getServerName() + " 353 " + this.nickName + " = " + channelName + " :");
                                for (String names : channelNames) {
                                    this.out.print(names);
                                }
                                this.out.println();
                            }
                        } else {
                            sendMessage(":" + IrcServerMain.getServerName() + " 400 * :You need to register first");
                        }
                        break;

                    case "LIST": // command to request the names of all channels on the server
                        if (this.registered) {
                            ArrayList<String> channelNames = new ArrayList<>();
                            for (ChannelHandler ch : IrcServerMain.getChannels()) {
                                if (!channelNames.contains(ch.getChannelName())) { // get the channel names only once
                                    channelNames.add(ch.getChannelName());
                                }
                            }
                            for (String channel : channelNames) {
                                sendMessage(":" + IrcServerMain.getServerName() + " 322 " + this.nickName + " " + channel);
                            }
                            sendMessage(":" + IrcServerMain.getServerName() + " 323 " + this.nickName + " :End of LIST");

                        } else {
                            sendMessage(":" + IrcServerMain.getServerName() + " 400 * :You need to register first");
                        }
                        break;

                    case "TIME": // command to ask the server to respond with the current date and time
                        sendMessage(":" + IrcServerMain.getServerName() + " 391 * :" + LocalDateTime.now());
                        break;

                    case "INFO": // command to request some basic information about the server
                        sendMessage(":" + IrcServerMain.getServerName() + " 371 * :This is an IRC server replica, created by 220015821");
                        break;

                    case "PING": // command to communicate with the server
                        final int pingMessageLimit = 2;
                        String[] pingMessageSplit = message.split(" ", pingMessageLimit);
                        String pingSendMessage = pingMessageSplit[1];
                        sendMessage("PONG " + pingSendMessage);
                        break;

                    //----------------------EXTRA FEATURE------------------------
                    case "MULTITABLE": // command to print the multiplication table of a number----(EXTRA FEATURE)
                        final int multiTableMessageLimit = 2;
                        final int tableLength = 10;
                        try {
                            String[] multiTableMessageSplit = message.split(" ", multiTableMessageLimit);
                            int num = Integer.parseInt(multiTableMessageSplit[1]);
                            sendMessage("  -------------");
                            for (int i = 1; i <= tableLength; i++) {
                                sendMessage("  " + num + " * " + i + " = " + num * i);
                            }
                            sendMessage("  -------------");
                        } catch (Exception e) {
                            sendMessage("Usage: MULTITABLE <number>");
                        }


                    default: //default None
                }
            }
        } catch (IOException e) {
            shutdown();
        }
    }

    /**
     * Method to send the message to the client.
     *
     * @param message the message to be sent
     */
    public void sendMessage(String message) {
        this.out.println(message);
    }

    /**
     * Method to close the client connection to the server.
     */
    public void shutdown() {
        try {
            this.in.close();
            this.out.close();
            if (!this.client.isClosed()) {
                this.client.close();
            }
        } catch (IOException e) {
            // ignore;
        }
    }

    /**
     * Getter method to get the client.
     *
     * @return the client
     */
    public Socket getClient() {
        return this.client;
    }
}

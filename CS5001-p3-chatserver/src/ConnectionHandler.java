import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class ConnectionHandler implements Runnable {

    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private String nickName;
    private String userName;
    private String realName;
    private boolean registered;

    public ConnectionHandler(Socket client) {
        this.client = client;
        this.nickName = null;
        this.userName = null;
        this.realName = null;
        this.registered = false;
    }

    @Override
    public void run() {
        try {
            this.out = new PrintWriter(client.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            String message;
            String command;

            while ((message = this.in.readLine()) != null) {
                command = message.split(" ")[0];
                System.out.println(command);
                switch (command) {
                    case "NICK":
                        int nickMessageLimit = 2;
                        String[] nickMessageSplit = message.split(" ", nickMessageLimit);
                        if (nickMessageSplit.length == 2 && nickMessageSplit[1].matches("^[a-zA-Z_][A-Za-z0-9_]{1,9}")) {
                            this.nickName = nickMessageSplit[1];
                        } else {
                            sendMessage(":" + IrcServerMain.getServerName() + " 400 * :Invalid nickname");
                        }

                        break;


                    case "USER":
                        if (this.nickName != null) {
                            final int userMessagelimit = 5;
                            final int realNameIndex = 4;
                            String[] userMessageSplit = message.split(" ", userMessagelimit);
                            if (userMessageSplit.length == userMessagelimit && this.userName == null) {
                                if (userMessageSplit[realNameIndex].matches("^:[A-Za-z ]*")) {
                                    this.userName = userMessageSplit[1];
                                    this.realName = userMessageSplit[realNameIndex].replaceAll(":", "");
                                    this.registered = true;
                                    sendMessage(":" + IrcServerMain.getServerName() + " 001 " + this.nickName + " :Welcome to the IRC network, " + this.nickName);
                                } else {
                                    sendMessage(":" + IrcServerMain.getServerName() + " 400 * :Invalid arguments to USER command");
                                }
                            } else if (this.userName != null) {
                                sendMessage(":" + IrcServerMain.getServerName() + " 400 * :You are already registered");
                            } else if (userMessageSplit.length < userMessagelimit) {
                                sendMessage(":" + IrcServerMain.getServerName() + " 400 * :Not enough arguments");
                            }
                        } else {
                            sendMessage("Usage: USER <username> 0 * :<real_name>");
                        }

                        break;

                    case "QUIT":
                        if (this.registered) {
                            ArrayList<ChannelHandler> removeClientsFromChannel = new ArrayList<>();
                            IrcServerMain.broadCast(":" + this.nickName + " QUIT");
                            // Todo : delete this client from all channels
                            for (ChannelHandler ch : IrcServerMain.getChannels()) {
                                if (ch.getConnection().client.equals(this.client)) {
                                    removeClientsFromChannel.add(ch);
                                }
                            }
                            IrcServerMain.getChannels().removeAll(removeClientsFromChannel);
                            shutdown();
                        }

                        break;

                    case "JOIN":
                        int joinMessagelimit = 2;
                        String[] joinMessageSplit = message.split(" ", joinMessagelimit);
                        if (joinMessageSplit[1].matches("^#[A-Za-z0-9_]*") && this.registered) {
                            ChannelHandler channel = new ChannelHandler(joinMessageSplit[1], this);
                            channel.addClientToChannel();
                            IrcServerMain.broadCastToChannels(":" + this.nickName + " JOIN " + channel.getChannelName(), channel.getChannelName());
                        } else if (!this.registered && joinMessageSplit[1].matches("^#[A-Za-z0-9_]*")) {
                            sendMessage(":" + IrcServerMain.getServerName() + " 400 * :You need to register first");
                        } else {
                            sendMessage(":" + IrcServerMain.getServerName() + " 400 * :Invalid channel name");
                        }

                        break;

                    case "PART":
                        boolean channelExistsForPart = false;
                        int partMessagelimit = 2;
                        String[] partMessageSplit = message.split(" ", partMessagelimit);
                        if (!this.registered) {
                            sendMessage(":" + IrcServerMain.getServerName() + " 400 * :You need to register first");
                        } else {
                            ArrayList<ChannelHandler> removeClientsFromChannel = new ArrayList<>();
                            for (ChannelHandler ch : IrcServerMain.getChannels()) {
                                if (ch.getChannelName().equals(partMessageSplit[1]) && this.registered && (ch.getConnection().client == this.client)) {
                                    channelExistsForPart = true;
                                    IrcServerMain.broadCastToChannels(":" + this.nickName + " PART " + ch.getChannelName(), ch.getChannelName());
                                    removeClientsFromChannel.add(ch);
                                }
                            }
                            if (!channelExistsForPart) {
                                sendMessage(":" + IrcServerMain.getServerName() + " 400 " + this.nickName + " :No channel exists with that name");
                            } else {
                                IrcServerMain.getChannels().removeAll(removeClientsFromChannel);
                            }
                        }

                        break;

                    case "PRIVMSG":
                        final int privateMessagelimit = 3;
                        String[] privateMessageSplit = message.split(" ", privateMessagelimit);
                        String target = privateMessageSplit[1];
                        String privateSendMessage = privateMessageSplit[2].replaceAll(":", "");

                        if (target.matches("^#[A-Za-z0-9_]*") && this.registered && privateMessageSplit.length == privateMessagelimit) {
                            boolean channelExistsForPrivate = false;
                            for (ChannelHandler ch : IrcServerMain.getChannels()) {
                                if (ch.getChannelName().equals(target)) {
                                    channelExistsForPrivate = true;
                                }
                            }
                            if (channelExistsForPrivate) {
                                IrcServerMain.broadCastToChannels(":" + this.nickName + " PRIVMSG " + target + " :" + privateSendMessage, target);
                            } else {
                                sendMessage(":" + IrcServerMain.getServerName() + " 400 * :No channel exists with that name");
                            }
                        } else if (this.registered) {
                            boolean nickNameExists = false;
                            for (ConnectionHandler ch : IrcServerMain.getConnections()) {
                                if (ch.nickName.equals(target)) {
                                    nickNameExists = true;
                                    ch.out.println(":" + this.nickName + " PRIVMSG " + target + " :" + privateSendMessage);
                                }
                            }
                            if (!nickNameExists) {
                                sendMessage(":" + IrcServerMain.getServerName() + " 400 * :No user exists with that name");
                            }
                        } else if (!this.registered) {
                            sendMessage(":" + IrcServerMain.getServerName() + " 400 * :You need to register first");
                        } else if (privateMessageSplit.length < privateMessagelimit) {
                            sendMessage(":" + IrcServerMain.getServerName() + " 400 * :Invalid arguments to PRIVMSG command");
                        }

                        break;

                    case "NAMES":
                        if (this.registered) {
                            int namesMessagelimit = 2;
                            String[] namesMessageSplit = message.split(" ", namesMessagelimit);
                            String channelName = namesMessageSplit[1];
                            boolean channelExistsForNames = false;
                            ArrayList<String> channelNames = new ArrayList<>();

                            for (ChannelHandler ch : IrcServerMain.getChannels()) {
                                if (ch.getChannelName().equals(channelName)) {
                                    channelExistsForNames = true;
                                    channelNames.add(ch.getConnection().nickName);
                                }
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

                    case "LIST":
                        if (this.registered) {
                            for (ChannelHandler ch : IrcServerMain.getChannels()) {
                                sendMessage(":" + IrcServerMain.getServerName() + " 322 " + this.nickName + " " + ch.getChannelName());
                            }
                            sendMessage(":" + IrcServerMain.getServerName() + " 323 " + this.nickName + " :End of LIST");

                        } else {
                            sendMessage(":" + IrcServerMain.getServerName() + " 400 * :You need to register first");
                        }

                        break;

                    case "TIME":
                        TimeZone tz = TimeZone.getTimeZone("UTC");
                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
                        df.setTimeZone(tz);
                        String nowAsISO = df.format(new Date());
                        sendMessage(":" + IrcServerMain.getServerName() + " 391 * :" + nowAsISO);

                        break;

                    case "INFO":
                        sendMessage(":" + IrcServerMain.getServerName() + " 371 * :This is an IRC server replica, created by 220015821");
                        break;

                    case "PING":
                        int limit = 2;
                        String[] messageSplit = message.split(" ", limit);
                        String pingSendMessage = messageSplit[1];
                        sendMessage("PONG " + pingSendMessage);

                        break;

                    default: //default
                }
            }
        } catch (IOException e) {
            shutdown();
        }
    }

    public void sendMessage(String message) {
        this.out.println(message);
    }

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

    public Socket getClient() {
        return this.client;
    }
}

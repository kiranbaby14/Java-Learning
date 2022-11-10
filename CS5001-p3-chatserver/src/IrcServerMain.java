import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IrcServerMain implements Runnable {
    public static String serverName;
    public static int portNumber;
    private ArrayList<ConnectionHandler> connections;
    private ArrayList<ChannelHandler> channels;
    private ServerSocket server;
    private boolean done;
    private ExecutorService pool;

    public IrcServerMain() {
        this.connections = new ArrayList<>();
        this.channels = new ArrayList<>();
        this.done = false;
    }

    @Override
    public void run() {
        try {
            this.server = new ServerSocket(portNumber);
            this.pool = Executors.newCachedThreadPool();

            while (!this.done) {
                Socket client = this.server.accept();
                ConnectionHandler handler = new ConnectionHandler(client);
                this.connections.add(handler);
                this.pool.execute(handler);
            }


        } catch (Exception e) {
            shutdown();
        }
    }

    public void broadCast(String message) {
        for (ConnectionHandler ch : this.connections) {
            if (ch != null) {
                ch.sendMessage(message);
            }
        }
    }

    public void broadCastToChannels(String message, String channelName) {
        for (ChannelHandler ch : this.channels) {
            if (ch.connection.client != null && ch.channelName.equals(channelName)) {
                ch.connection.sendMessage(message);
            }
        }
    }

    public void shutdown() {
        try {
            done = true;
            pool.shutdown();
            if (!server.isClosed()) {
                server.close();
            }

            for (ConnectionHandler ch : connections) {
                ch.shutdown();
            }
        } catch (IOException e) {
            // ignore; cannot handle
        }

    }

    class ConnectionHandler implements Runnable {

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
//                out.println("Please enter a nickname: ");
//                nickName = in.readLine();
//                System.out.println(nickName + " connected....");
//                broadCast(nickName + " joined the chat.....");

                String message;
                while ((message = this.in.readLine()) != null) {

                    if (message.startsWith("NICK")) { // NICK Command

                        String[] messageSplit = message.split(" ", 2);
                        if (messageSplit.length == 2 && messageSplit[1].matches("^[a-zA-Z_][A-Za-z0-9_]{1,9}")) {
//                            broadCast(nickName + " renamed themselves to " + messageSplit[1]);
//                            System.out.println(nickName + " renamed themselves to " + messageSplit[1]);
                            this.nickName = messageSplit[1];
//                            out.println("Successfully changed nickname to " + nickName);
                        } else {
                            this.out.println(":" + serverName + " 400 * :Invalid nickname");
                        }

                    } else if (message.startsWith("USER")) {
                        if (this.nickName != null) {
                            String[] messageSplit = message.split(" ", 5);
                            if (messageSplit.length == 5 && this.userName == null) {
                                if (messageSplit[4].matches("^:[A-Za-z ]*")) {
                                    this.userName = messageSplit[1];
                                    this.realName = messageSplit[4].replaceAll(":", "");
                                    this.registered = true;
                                    this.out.println(":" + serverName + " 001 " + this.nickName + " :Welcome to the IRC network, " + this.nickName);
                                } else {
                                    this.out.println(":" + serverName + " 400 * :Invalid arguments to USER command");
                                }

                            } else if (this.userName != null) {
                                this.out.println(":" + serverName + " 400 * :You are already registered");
                            } else if (messageSplit.length < 5) {
                                this.out.println(":" + serverName + " 400 * :Not enough arguments");
                            }
                        } else {
                            this.out.println("Usage: USER <username> 0 * :<real_name>");
                        }


                    } else if (message.startsWith("QUIT") && this.registered) {
//                        broadCast(this.nickName + " left the chat...");
                        broadCast(":" + this.nickName + " QUIT");
                        this.out.println(":" + this.nickName + " QUIT");
                        // Todo : delete this client from all channels
                        shutdown();

                    } else if (message.startsWith("JOIN")) {
                        String[] messageSplit = message.split(" ", 2);
                        if (messageSplit[1].matches("^#[A-Za-z0-9_]*") && this.registered) {
                            ChannelHandler channel = new ChannelHandler(messageSplit[1], this);
                            channel.addClientToChannel();
                            broadCastToChannels(":" + this.nickName + " JOIN " + channel.channelName, channel.channelName);
                        } else if (!this.registered && messageSplit[1].matches("^#[A-Za-z0-9_]*")) {
                            this.out.println(":" + serverName + " 400 * :You need to register first");
                        } else {
                            this.out.println(":" + serverName + " 400 * :Invalid channel name");
                        }

                    } else if (message.startsWith("PART")) {
                        boolean channelExists = false;
                        String[] messageSplit = message.split(" ", 2);
                        if (!this.registered) {
                            this.out.println(":" + serverName + " 400 * :You need to register first");
                        } else {
                            for (ChannelHandler ch : channels) {
                                if (ch.channelName.equals(messageSplit[1]) && this.registered) {
                                    channelExists = true;
                                    broadCastToChannels(":" + this.nickName + " PART " + ch.channelName, ch.channelName);
                                    ch.removeClientFromChannel();
                                }
                            }

                            if (!channelExists) {
                                this.out.println(":" + serverName + " 400 " + this.nickName + " :No channel exists with that name");
                            }
                        }

                    } else if (message.startsWith("PRIVMSG ")) {
                        String[] messageSplit = message.split(" ", 3);
                        String target = messageSplit[1];
                        String sendMessage = messageSplit[2].replaceAll(":", "");

                        if (target.matches("\"^#[A-Za-z0-9_]*\"") && this.registered && messageSplit.length == 3) {
                            boolean channelExists = false;
                            for (ChannelHandler ch : channels) {
                                if (ch.channelName.equals(target)) {
                                    channelExists = true;
                                }
                            }

                            if (channelExists) {
                                broadCastToChannels(":" + this.nickName + " PRIVMSG " + target + " :" + sendMessage, target);
                            } else {
                                this.out.println(":" + serverName + " 400 * :No channel exists with that name");
                            }

                        } else if (this.registered) {
                            boolean nickNameExists = false;
                            for (ConnectionHandler ch : connections) {
                                if (ch.nickName.equals(target)) {
                                    nickNameExists = true;
                                    ch.out.println(":" + this.nickName + " PRIVMSG " + target + " :" + sendMessage);
                                }
                            }
                            if (!nickNameExists) {
                                this.out.println(":" + serverName + " 400 * :No user exists with that name");
                            }
                        } else if (!this.registered) {
                            this.out.println(":" + serverName + " 400 * :You need to register first");
                        } else if (messageSplit.length < 3) {
                            this.out.println(":" + serverName + " 400 * :Invalid arguments to PRIVMSG command");
                        }

                    } else if (message.startsWith("NAMES")) {
                        if (this.registered) {
                            String[] messageSplit = message.split(" ", 2);
                            String channelName = messageSplit[1];
                            boolean channelExists = false;
                            ArrayList<String> channelNames = new ArrayList<>();

                            for (ChannelHandler ch : channels) {
                                if (ch.channelName.equals(channelName)) {
                                    channelExists = true;
                                    channelNames.add(ch.connection.nickName);
                                }
                            }

                            if (!channelExists) {
                                this.out.println(":" + serverName + " 400 * :No channel exists with that name");
                            } else {
                                this.out.print(":" + serverName + " 353 " + this.nickName + " = " + channelName + " :");
                                for (String names : channelNames) {
                                    this.out.print(names);
                                }
                                this.out.println();
                            }

                        } else {
                            this.out.println(":" + serverName + " 400 * :You need to register first");
                        }

                    } else if (message.startsWith("LIST")) {
                        if (this.registered) {
                            for (ChannelHandler ch : channels) {
                                this.out.println(":" + serverName + " 322 " + this.nickName + " " + ch.channelName);
                            }
                            this.out.println(":" + serverName + " 323 " + this.nickName + " :End of LIST");

                        } else {
                            this.out.println(":" + serverName + " 400 * :You need to register first");
                        }

                    } else if (message.startsWith("TIME")) {

                    } else if (message.startsWith("INFO")) {

                    } else if (message.startsWith("PING")) {
                        String[] messageSplit = message.split(" ", 2);
                        String sendMessage = messageSplit[1];
                        this.sendMessage("PONG " + sendMessage);
                    } else {
                        // broadCast(this.nickName + ": " + message);
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
    }

    class ChannelHandler {
        private String channelName;
        private ConnectionHandler connection;

        public ChannelHandler(String channelName, ConnectionHandler connection) {
            this.channelName = channelName;
            this.connection = connection;
        }

        public void addClientToChannel() {
            channels.add(this);
        }

        public void removeClientFromChannel() {
            channels.remove(this);
        }
    }

    public static void main(String[] args) {

        if (args.length >= 2) {
            try {
                serverName = args[0];
                portNumber = Integer.parseInt(args[1]);
            } catch (Exception e) {
                System.out.println("Usage: java IrcServerMain <server_name> <port>");
            }

            IrcServerMain server = new IrcServerMain();
            server.run();

        } else {
            System.out.println("Usage: java IrcServerMain <server_name> <port>");
        }

    }
}

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Chat Server
 * A server that can contain multiple channels and clients.
 * Clients can interact with the server using various COMMAND keywords
 *
 * @author: Student ID: 220015821
 */
public class IrcServerMain implements Runnable {
    private static String serverName;
    private static int portNumber;
    private static ArrayList<ConnectionHandler> connections; // stores all the client connections
    private static ArrayList<ChannelHandler> channels; // stores all the channel information
    private ServerSocket server;
    private boolean done;
    private ExecutorService pool;

    /**
     * Constructor of the class IrcServerMain.
     * Initialises an arraylist for clients.
     * Initialises an arraylist for the channels.
     */
    public IrcServerMain() {
        connections = new ArrayList<>();
        channels = new ArrayList<>();
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
                connections.add(handler);
                this.pool.execute(handler);
            }
        } catch (Exception e) {
            shutdown();
        }
    }

    /**
     * Method to broadcast the message to all the clients in the server.
     *
     * @param message the message that needs to be broadcast
     */
    public static void broadCast(String message) {
        for (ConnectionHandler ch : connections) {
            if (ch != null) {
                ch.sendMessage(message);
            }
        }
    }

    /**
     * Method to broadcast the message to all the clients in a particular channel in the server.
     *
     * @param message the message that needs to be broadcast
     * @param channelName the channel to which the message should be broadcast
     */
    public static void broadCastToChannels(String message, String channelName) {
        for (ChannelHandler ch : channels) {
            if (ch.getConnection().getClient() != null && ch.getChannelName().equals(channelName)) {
                ch.getConnection().sendMessage(message);
            }
        }
    }

    /**
     * Method to shut down the server and all the clients.
     */
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
            // ignore
        }
    }

    /**
     * Getter method to get the server name.
     *
     * @return the server name
     */
    public static String getServerName() {
        return serverName;
    }

    /**
     * Getter method to get the port number.
     *
     * @return the port number
     */
    public static int getPortNumber() {
        return portNumber;
    }

    /**
     * Getter method to get the arraylist of the all the clients.
     *
     * @return the arraylist of the all the clients
     */
    public static ArrayList<ConnectionHandler> getConnections() {
        return connections;
    }

    /**
     * Getter method to get the arraylist of the all the channels in the server.
     *
     * @return the arraylist of the all the channels in the server
     */
    public static ArrayList<ChannelHandler> getChannels() {
        return channels;
    }

    /**
     * Main function.
     *
     * @param args the arguments taken from the CLI
     */
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

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IrcServerMain implements Runnable {
    private static String serverName;
    private static int portNumber;
    private static ArrayList<ConnectionHandler> connections;
    private static ArrayList<ChannelHandler> channels;
    private ServerSocket server;
    private boolean done;
    private ExecutorService pool;

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

    public static void broadCast(String message) {
        for (ConnectionHandler ch : connections) {
            if (ch != null) {
                ch.sendMessage(message);
            }
        }
    }

    public static void broadCastToChannels(String message, String channelName) {
        for (ChannelHandler ch : channels) {
            if (ch.getConnection().getClient() != null && ch.getChannelName().equals(channelName)) {
                ch.getConnection().sendMessage(message);
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
            // ignore
        }
    }

    public static String getServerName() {
        return serverName;
    }

    public static int getPortNumber() {
        return portNumber;
    }

    public static ArrayList<ConnectionHandler> getConnections() {
        return connections;
    }

    public static ArrayList<ChannelHandler> getChannels() {
        return channels;
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

/**
 * ChannelHandler
 * A class which handles all the channels in the server.
 * It inherits an interface of Runnable class.
 *
 * @author: Student ID: 220015821
 */
public class ChannelHandler {
    private IrcServerMain server;
    private String channelName;
    private ConnectionHandler connection;

    /**
     * Constructor of the class ChannelHandler.
     *
     * @param channelName the name of the channel
     * @param connection the client that wants to join the channel
     */
    public ChannelHandler(String channelName, ConnectionHandler connection) {
        this.channelName = channelName;
        this.connection = connection;
    }

    /**
     * Method to add a channel to the server.
     */
    public void addClientToChannel() {
        IrcServerMain.getChannels().add(this);
    }

    /**
     * getter method to get the channel name.
     *
     * @return the name of the channel
     */
    public String getChannelName() {
        return this.channelName;
    }

    /**
     * getter method to get client.
     *
     * @return the object of the connectionHandler class
     */
    public ConnectionHandler getConnection() {
        return this.connection;
    }

}

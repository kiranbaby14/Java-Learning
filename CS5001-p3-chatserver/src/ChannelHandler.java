public class ChannelHandler {
    private IrcServerMain server;
    private String channelName;
    private ConnectionHandler connection;


    public ChannelHandler(String channelName, ConnectionHandler connection) {
        this.channelName = channelName;
        this.connection = connection;
        this.server = new IrcServerMain();
    }

    public void addClientToChannel() {
        IrcServerMain.getChannels().add(this);
    }

//    public void removeClientFromChannel() {
//        IrcServerMain.getChannels().remove(this);
//    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public ConnectionHandler getConnection() {
        return connection;
    }

    public void setConnection(ConnectionHandler connection) {
        this.connection = connection;
    }
}

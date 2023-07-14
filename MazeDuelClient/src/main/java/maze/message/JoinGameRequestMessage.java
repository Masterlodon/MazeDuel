package maze.message;

public class JoinGameRequestMessage extends Message
{
    int hostId;

    public JoinGameRequestMessage(int hostId)
    {
        super(JoinGameRequestMessage.class);
        this.hostId = hostId;
    }

    public int getHostId()
    {
        return hostId;
    }
}

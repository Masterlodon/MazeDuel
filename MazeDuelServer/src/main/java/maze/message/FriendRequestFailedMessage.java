package maze.message;

public class FriendRequestFailedMessage extends Message
{
    private String contents;

    public FriendRequestFailedMessage(String contents)
    {
        super(FriendRequestFailedMessage.class);
        this.contents = contents;
    }

    public String getContents()
    {
        return contents;
    }
}

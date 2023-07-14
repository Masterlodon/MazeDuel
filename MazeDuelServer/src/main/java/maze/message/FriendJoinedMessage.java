package maze.message;

public class FriendJoinedMessage extends Message
{
    int userId;

    public FriendJoinedMessage(int userId)
    {
        super(FriendJoinedMessage.class);
        this.userId = userId;
    }

    public int getUserId()
    {
        return userId;
    }
}

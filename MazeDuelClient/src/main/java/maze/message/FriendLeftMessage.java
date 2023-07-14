package maze.message;

public class FriendLeftMessage extends Message
{
    int userId;

    public FriendLeftMessage(int userId)
    {
        super(FriendLeftMessage.class);
        this.userId = userId;
    }

    public int getUserId()
    {
        return userId;
    }
}

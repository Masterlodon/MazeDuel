package maze.message;

import maze.database.data.User;

public class FriendRequestRequestMessage extends Message
{
    private User user;

    public FriendRequestRequestMessage(User user)
    {
        super(FriendRequestRequestMessage.class);
        this.user = user;
    }

    public User getUser()
    {
        return user;
    }
}

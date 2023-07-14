package maze.message;

import maze.database.data.FriendRequest;
import maze.database.data.User;

public class FriendRequestSuccessMessage extends Message
{
    private FriendRequest friendRequest;
    private User receiver;

    public FriendRequestSuccessMessage(FriendRequest friendRequest, User receiver)
    {
        super(FriendRequestSuccessMessage.class);
        this.friendRequest = friendRequest;
        this.receiver = receiver;
    }

    public FriendRequest getFriendRequest()
    {
        return friendRequest;
    }

    public User getReceiver()
    {
        return receiver;
    }
}

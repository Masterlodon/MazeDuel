package maze.message;

import maze.database.data.FriendRequest;
import maze.database.data.User;

public class FriendRequestMessage extends Message
{
    private FriendRequest friendRequest;
    private User sender;

    public FriendRequestMessage(FriendRequest friendRequest, User sender)
    {
        super(FriendRequestMessage.class);
        this.friendRequest = friendRequest;
        this.sender = sender;
    }

    public FriendRequest getFriendRequest()
    {
        return friendRequest;
    }

    public User getSender()
    {
        return sender;
    }
}

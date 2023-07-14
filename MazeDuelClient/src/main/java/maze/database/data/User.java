package maze.database.data;

import gui.mazeduelclient.Application;
import maze.database.DBFriendRequest;
import maze.database.DBFriendship;
import maze.database.DBUser;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable
{
    private static final long serialVersionUID = "User".hashCode();
    private transient ArrayList<User> friends;
    private transient ArrayList<User> friendRequesters;
    private transient ArrayList<Friendship> friendships;
    private transient ArrayList<FriendRequest> friendRequests;
    private transient boolean match;
    private boolean online;
    private int id;
    private String userName;
    private int passwordHash;

    public User(int id, String userName, int passwordHash)
    {
        this.id = id;
        this.userName = userName;
        this.passwordHash = passwordHash;
    }

    public int getId()
    {
        return id;
    }

    public String getUserName()
    {
        return userName;
    }

    public int getPasswordHash()
    {
        return passwordHash;
    }

    public ArrayList<User> getOnlineFriends()
    {
        if(friends == null)
        {
            friends = DBUser.getFriendsByUser(this);
        }
        ArrayList<User> online = new ArrayList<>();
        for(User friend : friends)
        {
            if(friend.isOnline())
            {
                online.add(friend);
            }
        }
        return online;
    }

    public boolean isOnline()
    {
        return online;
    }

    public void setOnline(boolean online)
    {
        this.online = online;
        if(Application.getInstance().getActiveScene().equals("friends"))
        {
            Application.getInstance().getController().getFriendsController().loadFriends();
        }
    }

    public ArrayList<User> getFriends()
    {
        if(friends == null)
        {
            friends = DBUser.getFriendsByUser(this);
        }
        return friends;
    }

    public ArrayList<User> getFriendRequesters()
    {
        if(friendRequests == null)
        {
            friendRequesters = DBUser.getFriendRequestersByUser(this);
        }
        return friendRequesters;
    }

    public ArrayList<Friendship> getFriendships()
    {
        if(friendships == null)
        {
            friendships = DBFriendship.getFriendshipsByUser(this);
        }
        return friendships;
    }

    public ArrayList<FriendRequest> getFriendRequests()
    {
        if(friendRequests == null)
        {
            friendRequests = DBFriendRequest.getFriendRequestsByUser(this);
        }
        return friendRequests;
    }

    public boolean isMatch()
    {
        return match;
    }

    public void setMatch(boolean match)
    {
        this.match = match;
    }

    public void resetReferences()
    {
        friends = null;
        friendships = null;
        friendRequests = null;
        friendRequesters = null;
    }

    @Override
    public String toString()
    {
        return userName;
    }
}

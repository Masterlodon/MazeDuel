package maze.database.data;

import maze.database.DBFriendRequest;
import maze.database.DBFriendship;
import maze.database.DBUser;

import java.io.Serializable;
import java.util.ArrayList;

public class MainData implements Serializable
{
    private static final long serialVersionUID = "MainData".hashCode();
    private static MainData instance;
    private ArrayList<User> users;
    private ArrayList<Friendship> friendships;
    private ArrayList<FriendRequest> friendRequests;
    private User user;

    public MainData()
    {
        users = DBUser.getUsersFromDB();
        friendships = DBFriendship.getFriendshipsFromDB();
        friendRequests = DBFriendRequest.getFriendRequestFromDB();
    }

    public MainData(User user)
    {
        this.user = user;
        this.users = user.getFriends();
        this.users.addAll(user.getFriendRequesters());
        this.friendships = user.getFriendships();
        this.friendRequests = user.getFriendRequests();
    }

    public static MainData getInstance()
    {
        if(instance == null)
        {
            instance = new MainData();
        }
        return instance;
    }

    public ArrayList<User> getUsers()
    {
        return users;
    }

    public ArrayList<Friendship> getFriendships()
    {
        return friendships;
    }

    public ArrayList<FriendRequest> getFriendRequests()
    {
        return friendRequests;
    }

    public User getUser()
    {
        return user;
    }
}

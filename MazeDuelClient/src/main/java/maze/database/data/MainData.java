package maze.database.data;

import gui.mazeduelclient.Application;

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

    public static MainData getInstance()
    {
        return instance;
    }

    public static void setInstance(MainData instance)
    {
        MainData.instance = instance;
        Application.getInstance().getController().getAddFriendController().updateReceivedRequestCount();
        Application.getInstance().getController().getAddFriendController().updateSendRequestCount();
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

    public ArrayList<FriendRequest> getSendFriendRequests()
    {
        ArrayList<FriendRequest> friendRequests = new ArrayList<>();
        for(FriendRequest friendRequest : this.friendRequests)
        {
            if(friendRequest.getFriend0Nr() == user.getId())
            {
                friendRequests.add(friendRequest);
            }
        }
        return friendRequests;
    }

    public ArrayList<FriendRequest> getReceivedFriendRequests()
    {
        ArrayList<FriendRequest> friendRequests = new ArrayList<>();
        for(FriendRequest friendRequest : this.friendRequests)
        {
            if(friendRequest.getFriend1Nr() == user.getId())
            {
                friendRequests.add(friendRequest);
            }
        }
        return friendRequests;
    }

    public User getUser()
    {
        return user;
    }
}

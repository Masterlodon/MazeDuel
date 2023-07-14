package maze.database;

import maze.database.data.FriendRequest;
import maze.database.data.Friendship;
import maze.database.data.MainData;
import maze.database.data.User;

import java.util.ArrayList;

public class DBUser
{
    public static User getUserByUserName(String userName)
    {
        for(User user : MainData.getInstance().getUsers())
        {
            if(user.getUserName().equals(userName))
            {
                return user;
            }
        }
        return null;
    }

    public static User getUserById(int id)
    {
        for(User user : MainData.getInstance().getUsers())
        {
            if(user.getId() == id)
            {
                return user;
            }
        }
        return null;
    }

    public static ArrayList<User> getFriendsByUser(User user)
    {
        ArrayList<User> users = new ArrayList<>();
        for(Friendship friendship : MainData .getInstance().getFriendships())
        {
            if(friendship.getFriend0Nr() == user.getId())
            {
                users.add(DBUser.getUserById(friendship.getFriend1Nr()));
            }
            else if(friendship.getFriend1Nr() == user.getId())
            {
                users.add(DBUser.getUserById(friendship.getFriend0Nr()));
            }
        }
        return users;
    }

    public static ArrayList<User> getFriendRequestersByUser(User user)
    {
        ArrayList<User> users = new ArrayList<>();
        for(FriendRequest friendRequest : MainData .getInstance().getFriendRequests())
        {
            if(friendRequest.getFriend0Nr() == user.getId())
            {
                users.add(DBUser.getUserById(friendRequest.getFriend1Nr()));
            }
            else if(friendRequest.getFriend1Nr() == user.getId())
            {
                users.add(DBUser.getUserById(friendRequest.getFriend0Nr()));
            }
        }
        return users;
    }

    public static ArrayList<User> getOnlineFriendsByUser(User user)
    {
        ArrayList<User> users = new ArrayList<>();
        for(User friend : DBUser.getFriendsByUser(user))
        {
            if(friend.isOnline())
            {
                users.add(friend);
            }
        }
        return users;
    }
}

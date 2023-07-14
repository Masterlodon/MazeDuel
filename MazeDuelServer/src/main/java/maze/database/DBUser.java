package maze.database;

import maze.database.data.FriendRequest;
import maze.database.data.Friendship;
import maze.database.data.MainData;
import maze.database.data.User;
import maze.game.Game;

import java.sql.ResultSet;
import java.util.ArrayList;

public class DBUser
{
    public static ArrayList<User> getUsersFromDB()
    {
        ResultSet result = AccessConnection.selectCommand("select ID, userName, passwordHash from User;");
        return getUsersFromResult(result);
    }

    public static ArrayList<User> getUsersFromResult(ResultSet result)
    {
        try
        {
            ArrayList<User> users = new ArrayList<>();
            result.first();
            if(result.getRow() == 1)
            {
                users.add(getUserFromCurrentResult(result));
                while(result.next())
                {
                    users.add(getUserFromCurrentResult(result));
                }
            }
            return users;
        }
        catch (Exception e)
        {
            System.err.println("Error in DBUser.getUserFromResult.\n" + e);
        }
        return null;
    }

    public static User getUserFromCurrentResult(ResultSet result)
    {
        try
        {
            return new User(result.getInt(1), result.getString(2), result.getInt(3));
        }
        catch (Exception e)
        {
            System.err.println("Error in DBUser.getUserFromCurrentResult.\n" + e);
        }
        return null;
    }

    public static boolean addUserToDB(String userName, int passwordHash)
    {
        if(getUserByUserName(userName) == null)
        {
            try
            {
                AccessConnection.modifyCommand("insert into User (userName, passwordHash) values(?, ?);", new Object[]{userName, passwordHash});
                MainData.getInstance().getUsers().add(new User(userName, passwordHash));
                return true;
            }
            catch (Exception e)
            {
                System.err.println("Error in DBUser.addUserToDB.");
                e.printStackTrace();
            }
        }
        return false;
    }

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

    public static int checkLoginData(String userName, int passwordHash)
    {
        User user = getUserByUserName(userName);
        if(user == null)
        {
            //User not found
            return 0;
        }
        if(user.getPasswordHash() != passwordHash)
        {
            //Password incorrect
            return -1;
        }
        if(user.isOnline())
        {
            //User already logged in
            return -2;
        }
        //Login data correct
        return 1;
    }

    public static ArrayList<User> getUsersInGame(Game game)
    {
        ArrayList<User> users = new ArrayList<>();
        for(User user : MainData.getInstance().getUsers())
        {
            if(user.getGame() != null)
            {
                if(user.getGame() == game)
                {
                    users.add(user);
                }
            }
        }
        return users;
    }

    public static ArrayList<User> getUsersInGame(Game game, User exclude)
    {
        ArrayList<User> users = new ArrayList<>();
        for(User user : MainData.getInstance().getUsers())
        {
            if(user.getGame() != null)
            {
                if(user.getGame() == game && user != exclude)
                {
                    users.add(user);
                }
            }
        }
        return users;
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

    public static boolean areFriends(User user0, User user1)
    {
        for(User user : user0.getFriends())
        {
            if(user1.getId() == user.getId())
            {
                return true;
            }
        }
        return false;
    }

    public static boolean haveFriendRequest(User user0, User user1)
    {
        for(User user : user0.getFriendRequesters())
        {
            if(user1.getId() == user.getId())
            {
                return true;
            }
        }
        return false;
    }
}

package maze.database;

import maze.database.data.Friendship;
import maze.database.data.MainData;
import maze.database.data.User;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

public class DBFriendship
{
    public static ArrayList<Friendship> getFriendshipsFromDB()
    {
        ResultSet result = AccessConnection.selectCommand("select ID, friend0Nr, friend1Nr, timestampStart from Friendship;");
        return getFriendshipsFromResult(result);
    }

    public static ArrayList<Friendship> getFriendshipsFromResult(ResultSet result)
    {
        try
        {
            ArrayList<Friendship> friendships = new ArrayList<>();
            result.first();
            if(result.getRow() == 1)
            {
                friendships.add(getFriendshipFromCurrentResult(result));
                while(result.next())
                {
                    friendships.add(getFriendshipFromCurrentResult(result));
                }
            }
            return friendships;
        }
        catch (Exception e)
        {
            System.err.println("Error in DBFriendship.getFriendshipFromResult.\n" + e);
        }
        return null;
    }

    public static Friendship getFriendshipFromCurrentResult(ResultSet result)
    {
        try
        {
            return new Friendship(result.getInt(1), result.getInt(2), result.getInt(3), Timestamp.valueOf(result.getString(4)));
        }
        catch (Exception e)
        {
            System.err.println("Error in DBFriendship.getFriendshipFromCurrentResult.\n" + e);
        }
        return null;
    }

    public static boolean addFriendshipToDB(int friend0Nr, int friend1Nr, Timestamp timestampStart)
    {
        if(getFriendshipByFriends(friend0Nr, friend1Nr) == null)
        {
            try
            {
                AccessConnection.modifyCommand("insert into Friendship (friend0Nr, friend1Nr, timestampStart) values(?, ?, ?);", new Object[]{friend0Nr, friend1Nr, timestampStart.toString()});
                MainData.getInstance().getFriendships().add(new Friendship(friend0Nr, friend1Nr, timestampStart));
                DBUser.getUserById(friend0Nr).resetReferences();
                DBUser.getUserById(friend0Nr).resetReferences();
                return true;
            }
            catch (Exception e)
            {
                System.err.println("Error in DBFriendship.addFriendshipToDB.");
                e.printStackTrace();
            }
        }
        return false;
    }

    public static Friendship getFriendshipByFriends(int friend0Nr, int friend1Nr)
    {
        for(Friendship friendship : MainData.getInstance().getFriendships())
        {
            if(friendship.getFriend0Nr() == friend0Nr && friendship.getFriend1Nr() == friend1Nr)
            {
                return friendship;
            }
            else if(friendship.getFriend1Nr() == friend0Nr && friendship.getFriend0Nr() == friend1Nr)
            {
                return friendship;
            }
        }
        return null;
    }

    public static ArrayList<Friendship> getFriendshipsByUser(User user)
    {
        ArrayList<Friendship> friendships = new ArrayList<>();
        for(Friendship friendship : MainData.getInstance().getFriendships())
        {
            if(friendship.getFriend0Nr() == user.getId() || friendship.getFriend1Nr() == user.getId())
            {
                friendships.add(friendship);
            }
        }
        return friendships;
    }
}

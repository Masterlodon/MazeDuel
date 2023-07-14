package maze.database;

import maze.database.data.FriendRequest;
import maze.database.data.Friendship;
import maze.database.data.MainData;
import maze.database.data.User;

import java.sql.ResultSet;
import java.util.ArrayList;

public class DBFriendRequest
{
    public static ArrayList<FriendRequest> getFriendRequestFromDB()
    {
        ResultSet result = AccessConnection.selectCommand("select ID, friend0Nr, friend1Nr from FriendRequest;");
        return getFriendRequestFromResult(result);
    }

    public static ArrayList<FriendRequest> getFriendRequestFromResult(ResultSet result)
    {
        try
        {
            ArrayList<FriendRequest> friendRequests = new ArrayList<>();
            result.first();
            if(result.getRow() == 1)
            {
                friendRequests.add(getFriendRequestFromCurrentResult(result));
                while(result.next())
                {
                    friendRequests.add(getFriendRequestFromCurrentResult(result));
                }
            }
            return friendRequests;
        }
        catch (Exception e)
        {
            System.err.println("Error in DBFriendRequest.getFriendRequestFromResult.\n" + e);
        }
        return null;
    }

    public static FriendRequest getFriendRequestFromCurrentResult(ResultSet result)
    {
        try
        {
            return new FriendRequest(result.getInt(1), result.getInt(2), result.getInt(3));
        }
        catch (Exception e)
        {
            System.err.println("Error in DBFriendRequest.getFriendRequestFromCurrentResult.\n" + e);
        }
        return null;
    }

    public static boolean addFriendRequestToDB(int friend0Nr, int friend1Nr)
    {
        if(getFriendRequestByFriends(friend0Nr, friend1Nr) == null)
        {
            try
            {
                AccessConnection.modifyCommand("insert into FriendRequest (friend0Nr, friend1Nr) values(?, ?);", new Object[]{friend0Nr, friend1Nr});
                MainData.getInstance().getFriendRequests().add(new FriendRequest(friend0Nr, friend1Nr));
                DBUser.getUserById(friend0Nr).resetReferences();
                DBUser.getUserById(friend1Nr).resetReferences();
                return true;
            }
            catch (Exception e)
            {
                System.err.println("Error in DBFriendRequest.addFriendRequestToDB.");
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean addFriendRequestToDB(User user0, User user1)
    {
        return addFriendRequestToDB(user0.getId(), user1.getId());
    }

    public static FriendRequest getFriendRequestByFriends(int friend0Nr, int friend1Nr)
    {
        for(FriendRequest friendRequest : MainData.getInstance().getFriendRequests())
        {
            if(friendRequest.getFriend0Nr() == friend0Nr && friendRequest.getFriend1Nr() == friend1Nr)
            {
                return friendRequest;
            }
            else if(friendRequest.getFriend1Nr() == friend0Nr && friendRequest.getFriend0Nr() == friend1Nr)
            {
                return friendRequest;
            }
        }
        return null;
    }

    public static FriendRequest getFriendRequestByFriends(User user0, User user1)
    {
        return getFriendRequestByFriends(user0.getId(), user1.getId());
    }

    public static ArrayList<FriendRequest> getFriendRequestsByUser(User user)
    {
        ArrayList<FriendRequest> friendRequests = new ArrayList<>();
        for(FriendRequest friendRequest : MainData.getInstance().getFriendRequests())
        {
            if(friendRequest.getFriend0Nr() == user.getId() || friendRequest.getFriend1Nr() == user.getId())
            {
                friendRequests.add(friendRequest);
            }
        }
        return friendRequests;
    }
}

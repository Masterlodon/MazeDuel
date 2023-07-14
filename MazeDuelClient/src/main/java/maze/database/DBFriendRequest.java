package maze.database;

import maze.database.data.FriendRequest;
import maze.database.data.MainData;
import maze.database.data.User;

import java.util.ArrayList;

public class DBFriendRequest
{
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

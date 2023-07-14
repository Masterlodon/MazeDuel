package maze.database;

import maze.database.data.Friendship;
import maze.database.data.MainData;
import maze.database.data.User;

import java.util.ArrayList;

public class DBFriendship
{
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

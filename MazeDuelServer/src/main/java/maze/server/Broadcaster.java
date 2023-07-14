package maze.server;

import maze.database.data.MainData;
import maze.database.data.User;
import maze.game.GameJoinInfo;
import maze.message.FriendJoinedMessage;
import maze.message.FriendLeftMessage;
import maze.message.Message;
import maze.message.SendGamesMessage;

import java.util.ArrayList;

public class Broadcaster
{
    public static void updateGameInfo()
    {
        for(User user : MainData.getInstance().getUsers())
        {
            if(user.isInfoUpdates())
            {
                if(user.getInfoRequestType() == 'p')
                {
                    if(user.getClient() != null)
                    {
                        user.getClient().send(new SendGamesMessage(GameJoinInfo.getPublicInfo()));
                    }
                }
                else if(user.getInfoRequestType() == 'f')
                {
                    if(user.getClient() != null)
                    {
                        user.getClient().send(new SendGamesMessage(GameJoinInfo.getFriendsInfo(user)));
                    }
                }
            }
        }
    }

    public static void broadcast(ArrayList<User> users, Message message)
    {
        for(User user : users)
        {
            if(user.getClient() != null)
            {
                user.getClient().send(message);
            }
        }
    }

    public static void updateFriendsJoin(User user)
    {
        for(User friend : user.getFriends())
        {
            if(friend.getClient() != null)
            {
                friend.getClient().send(new FriendJoinedMessage(user.getId()));
            }
        }
    }

    public static void updateFriendsLeave(User user)
    {
        for(User friend : user.getFriends())
        {
            if(friend.getClient() != null)
            {
                friend.getClient().send(new FriendLeftMessage(user.getId()));
            }
        }
    }
}

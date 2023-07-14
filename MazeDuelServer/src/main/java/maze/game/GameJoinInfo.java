package maze.game;

import maze.database.DBUser;
import maze.database.data.User;
import maze.server.Server;

import java.io.Serializable;
import java.util.ArrayList;

public class GameJoinInfo implements Serializable
{
    private static final long serialVersionUID = "GameJoinInfo".hashCode();
    private String hostName;
    private int hostId;
    private int playerCount;
    private int width;
    private int height;

    public GameJoinInfo(String hostName, int hostId, int playerCount, int width, int height)
    {
        this.hostName = hostName;
        this.hostId = hostId;
        this.playerCount = playerCount;
        this.width = width;
        this.height = height;
    }

    public static ArrayList<GameJoinInfo> getPublicInfo()
    {
        ArrayList<GameJoinInfo> info = new ArrayList<>();
        for(Game game : Server.getInstance().getGames())
        {
            info.add(new GameJoinInfo(game.getHost().getUser().getUserName(), game.getHost().getUser().getId(), game.getCompetitors().size(), game.getWidth(), game.getHeight()));
        }
        return info;
    }

    public static ArrayList<GameJoinInfo> getFriendsInfo(User user)
    {
        ArrayList<GameJoinInfo> info = new ArrayList<>();
        for(Game game : Server.getInstance().getGames())
        {
            if(user.isFriendsWith(game.getHost().getUser()))
            {
                info.add(new GameJoinInfo(game.getHost().getUser().getUserName(), game.getHost().getUser().getId(), game.getCompetitors().size(), game.getWidth(), game.getHeight()));
            }
        }
        return info;
    }
}

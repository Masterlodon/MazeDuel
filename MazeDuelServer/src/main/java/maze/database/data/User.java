package maze.database.data;

import maze.database.AccessConnection;
import maze.database.DBFriendRequest;
import maze.database.DBFriendship;
import maze.database.DBUser;
import maze.game.Competitor;
import maze.game.Game;
import maze.message.UpdateCompetitorsMessage;
import maze.server.Broadcaster;
import maze.server.ClientProxy;
import maze.server.Server;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;

public class User implements Serializable
{
    private static final long serialVersionUID = "User".hashCode();
    private transient boolean infoUpdates;
    private transient ClientProxy client;
    private transient Game game;
    private transient Competitor competitor;
    private transient char infoRequestType;
    private transient ArrayList<User> friends;
    private transient ArrayList<User> friendRequesters;
    private transient ArrayList<Friendship> friendships;
    private transient ArrayList<FriendRequest> friendRequests;
    private boolean online;
    private int id;
    private String userName;
    private int passwordHash;

    public User(int id, String userName, int passwordHash)
    {
        infoRequestType = 'f';
        infoUpdates = false;
        online = false;
        this.id = id;
        this.userName = userName;
        this.passwordHash = passwordHash;
    }

    public User(String userName, int passwordHash)
    {
        infoRequestType = 'f';
        infoUpdates = false;
        online = false;
        this.userName = userName;
        this.passwordHash = passwordHash;
        try
        {
            ResultSet result = AccessConnection.selectCommand("select max(ID) from User;");
            result.first();
            id = result.getInt(1);
        }
        catch (Exception e)
        {
            System.err.println("Error in user.Constructor.");
            e.printStackTrace();
        }
    }

    public ClientProxy getClient()
    {
        return client;
    }

    public void setClient(ClientProxy client)
    {
        this.client = client;
    }

    public Game getGame()
    {
        return game;
    }

    public void setGame(Game game)
    {
        this.game = game;
    }

    public int getId()
    {
        return id;
    }

    public String getUserName()
    {
        return userName;
    }

    public int getPasswordHash()
    {
        return passwordHash;
    }

    public boolean isOnline()
    {
        return online;
    }

    public void setOnline(boolean online)
    {
        this.online = online;
        if(online)
        {
            Broadcaster.updateFriendsJoin(this);
        }
        else
        {
            Broadcaster.updateFriendsLeave(this);
            if(game != null)
            {
                game.removeCompetitor(this);
                Broadcaster.broadcast(DBUser.getUsersInGame(game, this), new UpdateCompetitorsMessage(game));
                Server.getInstance().removeEmptyGames();
            }
        }
    }

    public boolean isInfoUpdates()
    {
        return infoUpdates;
    }

    public void setInfoUpdates(boolean infoUpdates)
    {
        this.infoUpdates = infoUpdates;
    }

    public char getInfoRequestType()
    {
        return infoRequestType;
    }

    public void setInfoRequestType(char infoRequestType)
    {
        this.infoRequestType = infoRequestType;
    }

    public Competitor getCompetitor()
    {
        return competitor;
    }

    public ArrayList<User> getFriends()
    {
        if(friends == null)
        {
            friends = DBUser.getFriendsByUser(this);
        }
        return friends;
    }

    public ArrayList<User> getFriendRequesters()
    {
        if(friendRequests == null)
        {
            friendRequesters = DBUser.getFriendRequestersByUser(this);
        }
        return friendRequesters;
    }

    public ArrayList<Friendship> getFriendships()
    {
        if(friendships == null)
        {
            friendships = DBFriendship.getFriendshipsByUser(this);
        }
        return friendships;
    }

    public ArrayList<FriendRequest> getFriendRequests()
    {
        if(friendRequests == null)
        {
            friendRequests = DBFriendRequest.getFriendRequestsByUser(this);
        }
        return friendRequests;
    }

    public void setCompetitor(Competitor competitor)
    {
        this.competitor = competitor;
    }

    public void resetReferences()
    {
        friends = null;
        friendships = null;
        friendRequests = null;
        friendRequesters = null;
    }

    public boolean isFriendsWith(User user)
    {
        return DBUser.areFriends(this, user);
    }

    public boolean hasFriendRequestWith(User user)
    {
        return DBUser.haveFriendRequest(this, user);
    }

    @Override
    public String toString()
    {
        return "ID: " + id + ", UserName: " + userName;
    }
}

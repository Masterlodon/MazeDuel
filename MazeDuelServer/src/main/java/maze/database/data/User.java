package maze.database.data;

import maze.game.Game;
import maze.server.ClientProxy;

import java.io.Serializable;

public class User implements Serializable
{
    private static final long serialVersionUID = "User".hashCode();
    private transient ClientProxy client;
    private transient Game game;
    private int id;
    private String userName;
    private int passwordHash;

    public User(int id, String userName, int passwordHash)
    {
        this.id = id;
        this.userName = userName;
        this.passwordHash = passwordHash;
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

    @Override
    public String toString()
    {
        return "Id: " + id + ", UserName: " + userName;
    }
}

package maze.game;

import java.io.Serializable;

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

    public String getHostName()
    {
        return hostName;
    }

    public int getHostId()
    {
        return hostId;
    }

    public int getPlayerCount()
    {
        return playerCount;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    @Override
    public String toString()
    {
        return "Host: " + hostName + ", Players: " + playerCount + ", Maze: " + width + " x " + height;
    }
}

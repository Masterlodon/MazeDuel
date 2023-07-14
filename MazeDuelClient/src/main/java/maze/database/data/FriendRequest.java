package maze.database.data;

import java.io.Serializable;

public class FriendRequest implements Serializable
{
    private static final long serialVersionUID = "FriendRequest".hashCode();
    private int id;
    private int friend0Nr;
    private int friend1Nr;

    public FriendRequest(int id, int friend0Nr, int friend1Nr)
    {
        this.id = id;
        this.friend0Nr = friend0Nr;
        this.friend1Nr = friend1Nr;
    }

    public int getId()
    {
        return id;
    }

    public int getFriend0Nr()
    {
        return friend0Nr;
    }

    public int getFriend1Nr()
    {
        return friend1Nr;
    }

    @Override
    public String toString()
    {
        return "ID: " + id + ", friend0Nr: " + friend0Nr + ", friend1Nr: " + friend1Nr;
    }
}

package maze.database.data;

import maze.database.AccessConnection;

import java.io.Serializable;
import java.sql.ResultSet;

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

    public FriendRequest(int friend0Nr, int friend1Nr)
    {
        this.friend0Nr = friend0Nr;
        this.friend1Nr = friend1Nr;
        try
        {
            ResultSet result = AccessConnection.selectCommand("select max(ID) from FriendRequest;");
            result.first();
            id = result.getInt(1);
        }
        catch (Exception e)
        {
            System.err.println("Error in friendRequest.Constructor.");
            e.printStackTrace();
        }
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

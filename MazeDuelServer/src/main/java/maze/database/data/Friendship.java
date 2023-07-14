package maze.database.data;

import maze.database.AccessConnection;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class Friendship implements Serializable
{
    private static final long serialVersionUID = "Friendship".hashCode();
    private int id;
    private int friend0Nr;
    private int friend1Nr;
    private Timestamp timestampStart;

    public Friendship(int id, int friend0Nr, int friend1Nr, Timestamp timestampStart)
    {
        this.id = id;
        this.friend0Nr = friend0Nr;
        this.friend1Nr = friend1Nr;
        this.timestampStart = timestampStart;
    }

    public Friendship(int friend0Nr, int friend1Nr, Timestamp timestampStart)
    {
        this.friend0Nr = friend0Nr;
        this.friend1Nr = friend1Nr;
        this.timestampStart = timestampStart;
        try
        {
            ResultSet result = AccessConnection.selectCommand("select max(ID) from Friendship;");
            result.first();
            id = result.getInt(1);
        }
        catch (Exception e)
        {
            System.err.println("Error in friendship.Constructor.");
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

    public Timestamp getTimestampStart()
    {
        return timestampStart;
    }

    @Override
    public String toString()
    {
        return "ID: " + id + ", Friend 0 Nr: " + friend0Nr + ", Friend 1 Nr: " + friend1Nr + ", Timestamp Start: " + timestampStart.toString();
    }
}

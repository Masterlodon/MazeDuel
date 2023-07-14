package maze.database.data;

import java.io.Serializable;
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

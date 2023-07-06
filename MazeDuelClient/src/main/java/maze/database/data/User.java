package maze.database.data;

import java.io.Serializable;

public class User implements Serializable
{
    private static final long serialVersionUID = "User".hashCode();
    private int id;
    private String userName;
    private int passwordHash;

    public User(int id, String userName, int passwordHash)
    {
        this.id = id;
        this.userName = userName;
        this.passwordHash = passwordHash;
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

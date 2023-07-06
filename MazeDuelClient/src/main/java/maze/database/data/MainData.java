package maze.database.data;

import java.io.Serializable;
import java.util.ArrayList;

public class MainData implements Serializable
{
    private static final long serialVersionUID = "MainData".hashCode();
    private static MainData instance;
    private ArrayList<User> users;
    private User user;

    public static MainData getInstance()
    {
        return instance;
    }

    public static void setInstance(MainData instance)
    {
        MainData.instance = instance;
    }

    public ArrayList<User> getUsers()
    {
        return users;
    }

    public User getUser()
    {
        return user;
    }
}

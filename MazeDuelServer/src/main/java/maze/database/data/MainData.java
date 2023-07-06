package maze.database.data;

import maze.database.DBUser;

import java.io.Serializable;
import java.util.ArrayList;

public class MainData implements Serializable
{
    private static final long serialVersionUID = "MainData".hashCode();
    private static MainData instance;
    private ArrayList<User> users;
    private User user;

    public MainData()
    {
        users = DBUser.getUsersFromDB();
    }

    public MainData(User user)
    {
        this.user = user;
    }

    public static MainData getInstance()
    {
        if(instance == null)
        {
            instance = new MainData();
        }
        return instance;
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

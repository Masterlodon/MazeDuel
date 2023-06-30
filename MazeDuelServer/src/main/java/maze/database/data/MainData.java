package maze.database.data;

import maze.database.DBUser;

import java.util.ArrayList;

public class MainData
{
    private static MainData instance;

    private ArrayList<User> users;

    public MainData()
    {
        users = DBUser.getUsersFromDB();
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
}

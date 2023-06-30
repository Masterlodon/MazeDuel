package maze.database.data;

public class MainData
{
    private static MainData instance;

    private User user;

    public MainData(User user)
    {
        this.user = user;
    }

    public static MainData getInstance()
    {
        return instance;
    }

    public static void setInstance(MainData instance)
    {
        MainData.instance = instance;
    }

    public User getUser()
    {
        return user;
    }
}

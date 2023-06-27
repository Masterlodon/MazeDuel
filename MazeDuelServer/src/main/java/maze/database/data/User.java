package maze.database.data;

public class User
{
    private int id;
    private String userName;
    private int passwordHash;

    public User(int id, String userName, int passwordHash)
    {
        this.id = id;
        this.userName = userName;
        this.passwordHash = passwordHash;
    }
}

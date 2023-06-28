package maze.message;

public class LoginMessage extends Message
{
    private String userName;
    private int passwordHash;

    public LoginMessage(String userName, int passwordHash)
    {
        super(LoginMessage.class);
        this.userName = userName;
        this.passwordHash = passwordHash;
    }

    public String getUserName()
    {
        return userName;
    }

    public int getPasswordHash()
    {
        return passwordHash;
    }
}

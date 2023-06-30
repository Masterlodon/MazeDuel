package maze.message;

public class LoginRequestMessage extends Message
{
    private String userName;
    private int passwordHash;

    public LoginRequestMessage(String userName, int passwordHash)
    {
        super(LoginRequestMessage.class);
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

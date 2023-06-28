package maze.message;

public class SignUpMessage extends Message
{
    private String userName;
    private int passwordHash;

    public SignUpMessage(String userName, int passwordHash)
    {
        super(SignUpMessage.class);
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

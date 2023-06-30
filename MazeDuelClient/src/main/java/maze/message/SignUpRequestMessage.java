package maze.message;

public class SignUpRequestMessage extends Message
{
    private String userName;
    private int passwordHash;

    public SignUpRequestMessage(String userName, int passwordHash)
    {
        super(SignUpRequestMessage.class);
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

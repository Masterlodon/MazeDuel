package maze.message;

public class LoginFailedMessage extends Message
{
    private String contents;

    public LoginFailedMessage(String contents)
    {
        super(LoginFailedMessage.class);
        this.contents = contents;
    }

    public String getContents()
    {
        return contents;
    }
}

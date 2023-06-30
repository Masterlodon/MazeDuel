package maze.message;

public class SignUpFailedMessage extends Message
{
    private String contents;
    public SignUpFailedMessage(String contents)
    {
        super(SignUpFailedMessage.class);
        this.contents = contents;
    }

    public String getContents()
    {
        return contents;
    }
}

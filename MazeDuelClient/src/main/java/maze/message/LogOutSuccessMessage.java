package maze.message;

public class LogOutSuccessMessage extends Message
{
    public LogOutSuccessMessage()
    {
        super(LoginSuccessMessage.class);
    }
}
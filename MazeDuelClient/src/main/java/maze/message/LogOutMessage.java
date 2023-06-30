package maze.message;

import maze.database.data.User;

public class LogOutMessage extends Message
{
    private User user;

    public LogOutMessage(User user)
    {
        super(LogOutMessage.class);
        this.user = user;
    }

    public User getUser()
    {
        return user;
    }
}

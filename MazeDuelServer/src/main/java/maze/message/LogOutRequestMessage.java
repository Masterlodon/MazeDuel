package maze.message;

import maze.database.data.User;

public class LogOutRequestMessage extends Message
{
    private User user;

    public LogOutRequestMessage(User user)
    {
        super(LogOutRequestMessage.class);
        this.user = user;
    }

    public User getUser()
    {
        return user;
    }
}
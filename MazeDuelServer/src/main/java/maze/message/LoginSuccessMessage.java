package maze.message;

import maze.database.data.User;

public class LoginSuccessMessage extends Message
{
    private User user;

    public LoginSuccessMessage(User user)
    {
        super(LoginSuccessMessage.class);
        this.user = user;
    }

    public User getUser()
    {
        return user;
    }
}

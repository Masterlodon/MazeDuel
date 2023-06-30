package maze.message;

import maze.database.data.User;

public class SignUpSuccessMessage extends Message
{
    private User user;

    public SignUpSuccessMessage(User user)
    {
        super(SignUpSuccessMessage.class);
        this.user = user;
    }

    public User getUser()
    {
        return user;
    }
}

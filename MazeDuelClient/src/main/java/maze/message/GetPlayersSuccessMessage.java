package maze.message;

import maze.database.data.User;

import java.util.ArrayList;

public class GetPlayersSuccessMessage extends Message
{
    private ArrayList<User> users;

    public GetPlayersSuccessMessage(ArrayList<User> users)
    {
        super(GetPlayersSuccessMessage.class);
        this.users = users;
    }

    public ArrayList<User> getUsers()
    {
        return users;
    }
}

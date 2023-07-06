package maze.message;

import maze.database.data.MainData;

public class LoginSuccessMessage extends Message
{
    private MainData mainData;

    public LoginSuccessMessage(MainData mainData)
    {
        super(LoginSuccessMessage.class);
        this.mainData = mainData;
    }

    public MainData getMainData()
    {
        return mainData;
    }
}

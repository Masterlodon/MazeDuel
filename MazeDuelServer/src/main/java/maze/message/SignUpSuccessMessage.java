package maze.message;

import maze.database.data.MainData;

public class SignUpSuccessMessage extends Message
{
    private MainData mainData;

    public SignUpSuccessMessage(MainData mainData)
    {
        super(SignUpSuccessMessage.class);
        this.mainData = mainData;
    }

    public MainData getMainData()
    {
        return mainData;
    }
}

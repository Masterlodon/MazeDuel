package maze.message;

public class RequestGamesMessage extends Message
{
    private char requestType;

    public RequestGamesMessage(char requestType)
    {
        super(RequestGamesMessage.class);
        this.requestType = requestType;
    }

    public char getRequestType()
    {
        return requestType;
    }
}

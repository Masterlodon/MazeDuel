package maze.message;

public class NewGameRequestMessage extends Message
{
    private int width;
    private int height;
    private boolean publicGame;

    public NewGameRequestMessage(int width, int height, boolean publicGame)
    {
        super(NewGameRequestMessage.class);
        this.width = width;
        this.height = height;
        this.publicGame = publicGame;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public boolean isPublicGame()
    {
        return publicGame;
    }
}

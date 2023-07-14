package maze.message;

import maze.game.Game;

public class JoinGameSuccessMessage extends Message
{
    private Game game;

    public JoinGameSuccessMessage(Game game)
    {
        super(JoinGameSuccessMessage.class);
        this.game = game;
    }

    public Game getGame()
    {
        return game;
    }
}

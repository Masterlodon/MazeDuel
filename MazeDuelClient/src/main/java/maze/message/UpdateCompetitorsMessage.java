package maze.message;

import maze.game.Game;

public class UpdateCompetitorsMessage extends Message
{
    private Game game;

    public UpdateCompetitorsMessage(Game game)
    {
        super(UpdateCompetitorsMessage.class);
        this.game = game;
    }

    public Game getGame()
    {
        return game;
    }
}

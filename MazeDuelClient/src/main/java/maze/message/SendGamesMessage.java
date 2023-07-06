package maze.message;

import maze.game.Game;

import java.util.ArrayList;

public class SendGamesMessage extends Message
{
    private ArrayList<Game> games;

    public SendGamesMessage(ArrayList<Game> games)
    {
        super(SendGamesMessage.class);
        this.games = games;
    }

    public ArrayList<Game> getGames()
    {
        return games;
    }
}

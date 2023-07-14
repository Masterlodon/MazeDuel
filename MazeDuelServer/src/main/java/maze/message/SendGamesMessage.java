package maze.message;

import maze.game.GameJoinInfo;

import java.util.ArrayList;

public class SendGamesMessage extends Message
{
    private ArrayList<GameJoinInfo> info;

    public SendGamesMessage(ArrayList<GameJoinInfo> info)
    {
        super(SendGamesMessage.class);
        this.info = info;
    }

    public ArrayList<GameJoinInfo> getInfo()
    {
        return info;
    }
}

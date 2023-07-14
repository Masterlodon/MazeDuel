package maze.server;

import maze.database.data.MainData;
import maze.game.Game;

import java.util.ArrayList;
import java.util.Hashtable;

public class Server
{
    private Entrance entrance;
    private static Server instance;
    private Hashtable<Integer, ClientProxy> clients;
    private ArrayList<Game> games;

    public static void main(String[] args)
    {
        MainData.getInstance();
        instance = new Server();
    }

    public Server()
    {
        games = new ArrayList<>();
        clients = new Hashtable<>();
        entrance = new Entrance();
    }

    public static Server getInstance()
    {
        return instance;
    }

    public void addClient(ClientProxy client)
    {
        clients.put(client.getSocket().getPort(), client);
    }

    public void removeClient(ClientProxy client)
    {
        clients.remove(client);
    }

    public void addGame(Game game)
    {
        games.add(game);
        Broadcaster.updateGameInfo();
    }

    public void removeGame(Game game)
    {
        games.remove(game);
        Broadcaster.updateGameInfo();
    }

    public ArrayList<Game> getGames()
    {
        return games;
    }

    public void removeEmptyGames()
    {
        ArrayList<Game> removed = new ArrayList<>();
        for(Game game : games)
        {
            if(game.getCompetitors().size() == 0)
            {
                removed.add(game);
            }
        }
        for(Game game : removed)
        {
            games.remove(game);
        }
        Broadcaster.updateGameInfo();
    }

    public void displayGames()
    {
        System.out.println("Current games:");
        for(Game game : games)
        {
            System.out.println(game + "\n");
        }
    }
}

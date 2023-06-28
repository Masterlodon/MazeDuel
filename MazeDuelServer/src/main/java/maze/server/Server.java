package maze.server;

import java.util.Hashtable;

public class Server
{
    private Entrance entrance;
    private static Server instance;

    private Hashtable<Integer, ClientProxy> clients;

    public static void main(String[] args)
    {
        instance = new Server();
    }

    public Server()
    {
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
}

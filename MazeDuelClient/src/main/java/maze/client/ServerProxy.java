package maze.client;

import maze.message.Message;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerProxy implements Runnable
{
    private boolean exit;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ServerProxy()
    {
        exit = false;
        try
        {
            socket = new Socket("localhost", 5555);
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
        }
        catch (Exception e)
        {
            System.err.println("Error in serverProxy.Constructor.");
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        Message o;

        try
        {

        }
        catch (Exception e)
        {
            System.err.println("Error in serverProxy.run.");
            e.printStackTrace();
        }
    }

    public void send(Message message)
    {
        //TODO
    }
}

package maze.server;

import java.net.ServerSocket;
import java.net.Socket;

public class Entrance implements Runnable
{
    private boolean exit;

    public Entrance()
    {
        exit = false;
        new Thread(this).start();
    }

    @Override
    public void run()
    {
        try
        {
            while(!exit)
            {
                ServerSocket server = new ServerSocket(5555);
                System.out.println("The server is now listening.");

                Socket socket = server.accept();
                Server.getInstance().addClient(new ClientProxy(socket));
                server.close();
                System.out.println("Client with socket [" + socket.toString() + "] has connected.");
            }
        }
        catch (Exception e)
        {
            System.err.println("Error in entrance.run.");
            e.printStackTrace();
        }
    }

    public void stop()
    {
        exit = true;
    }
}

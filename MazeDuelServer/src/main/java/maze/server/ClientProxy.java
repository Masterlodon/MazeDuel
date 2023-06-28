package maze.server;

import maze.message.Message;

import java.io.*;
import java.net.Socket;

public class ClientProxy implements Runnable
{
    private boolean exit;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public Socket getSocket()
    {
        return socket;
    }

    public ClientProxy(Socket socket)
    {
        exit = false;
        this.socket = socket;
        try
        {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            new Thread(this).start();
        }
        catch (Exception e)
        {
            System.out.println("Error in clientProxy.Constructor");
        }
    }

    @Override
    public void run()
    {
        Message o;

        try
        {
            while((o = (Message) in.readObject()) != null)
            {

            }
        }
        catch (Exception e)
        {
            System.out.println("Error in clientProxy.run.");
            e.printStackTrace();
        }
    }
}

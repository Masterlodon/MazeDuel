package maze.server;

import maze.message.LoginMessage;
import maze.message.Message;
import maze.message.SignUpMessage;

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
            System.err.println("Error in clientProxy.Constructor");
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        Message o;

        try
        {
            while((o = (Message) in.readObject()) != null && !exit)
            {
                if(o.getType() == LoginMessage.class)
                {
                    LoginMessage message = (LoginMessage) o;
                    System.out.println(message.getUserName() + " tried to log in with " + message.getPasswordHash() + ".");
                }
                else if(o.getType() == SignUpMessage.class)
                {
                    SignUpMessage message = (SignUpMessage) o;
                    System.out.println(message.getUserName() + " tried to sign up with " + message.getPasswordHash() + ".");
                }
                else
                {
                    System.err.println("Unrecognizable message detected.\n" + o);
                }
            }
        }
        catch (Exception e)
        {
            System.err.println("Error in clientProxy.run.");
            e.printStackTrace();
        }
    }

    public void send(Message message)
    {
        //TODO
    }
}

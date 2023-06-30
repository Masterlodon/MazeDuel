package maze.client;

import gui.mazeduelclient.Application;
import javafx.application.Platform;
import maze.database.data.MainData;
import maze.message.*;

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
            socket = new Socket("localhost", 8006);
            out = new ObjectOutputStream(socket.getOutputStream());
            new Thread(this).start();
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
            in = new ObjectInputStream(socket.getInputStream());
            while((o = (Message) in.readObject()) != null && !exit)
            {
                if(o.getType() == LoginFailedMessage.class)
                {
                    receiveLoginFailedMessage((LoginFailedMessage) o);
                }
                else if(o.getType() == LoginSuccessMessage.class)
                {
                    receiveLoginSuccessMessage((LoginSuccessMessage) o);
                }
                else if(o.getType() == SignUpFailedMessage.class)
                {
                    receiveSignUpFailedMessage((SignUpFailedMessage) o);
                }
                else if(o.getType() == SignUpSuccessMessage.class)
                {
                    receiveSignUpSuccessMessage((SignUpSuccessMessage) o);
                }
                else
                {
                    System.err.println("Unrecognizable message detected.\n" + o);
                }
            }
        }
        catch (Exception e)
        {
            System.err.println("Error in serverProxy.run.");
            e.printStackTrace();
        }
    }

    public void send(Message message)
    {
        try
        {
            out.writeObject(message);
            out.reset();
        }
        catch (Exception e)
        {
            System.err.println("Error in clientProxy.send.");
            e.printStackTrace();
        }
    }

    public void receiveLoginFailedMessage(LoginFailedMessage message)
    {
        Application.getInstance().getController().getLoginController().setWaitingForResponse(false);
        System.out.println(message.getContents());
    }

    public void receiveLoginSuccessMessage(LoginSuccessMessage message)
    {
        MainData.setInstance(new MainData(message.getUser()));
        System.out.println("Successfully logged in as " + message.getUser().getUserName() + ".");
        Platform.runLater(() ->
        {
            Application.getInstance().loadSceneMainScreen();
        });
    }

    public void receiveSignUpFailedMessage(SignUpFailedMessage message)
    {
        Application.getInstance().getController().getSignUpController().setWaitingForResponse(false);
        System.out.println(message.getContents());
    }

    public void receiveSignUpSuccessMessage(SignUpSuccessMessage message)
    {
        MainData.setInstance(new MainData(message.getUser()));
        System.out.println("Successfully signed up as " + message.getUser().getUserName() + ".");
        Platform.runLater(() ->
        {
            Application.getInstance().loadSceneMainScreen();
        });
    }
}
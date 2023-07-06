package maze.client;

import gui.mazeduelclient.Application;
import javafx.application.Platform;
import maze.database.data.MainData;
import maze.message.*;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

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
                else if(o.getType() == LogOutSuccessMessage.class)
                {
                    receiveLogOutSuccessMessage();
                }
                else if(o.getType() == NewGameSuccessMessage.class)
                {
                    receiveNewGameSuccessMessage((NewGameSuccessMessage) o);
                }
                else if(o.getType() == LeaveGameSuccessMessage.class)
                {
                    receiveLeaveGameSuccessMessage((LeaveGameSuccessMessage) o);
                }
                else
                {
                    System.err.println("Unrecognizable message detected.\n" + o);
                }
            }
        }
        catch (Exception e)
        {
            try
            {
                socket.close();
            }
            catch (Exception ex)
            {
                System.err.println("Error in serverProxy.run.");
                ex.printStackTrace();
            }
            System.err.println("Connection to server was lost.");
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
        System.out.println(message.getContents());
        Application.getInstance().getController().setWaitingForResponse(false);
    }

    public void receiveLoginSuccessMessage(LoginSuccessMessage message)
    {
        MainData.setInstance(message.getMainData());
        System.out.println("Successfully logged in as " + message.getMainData().getUser().getUserName() + ".");
        Platform.runLater(() ->
        {
            Application.getInstance().loadSceneMainScreen();
        });
        Application.getInstance().getController().setWaitingForResponse(false);
    }

    public void receiveSignUpFailedMessage(SignUpFailedMessage message)
    {
        System.out.println(message.getContents());
        Application.getInstance().getController().setWaitingForResponse(false);
    }

    public void receiveSignUpSuccessMessage(SignUpSuccessMessage message)
    {
        MainData.setInstance(message.getMainData());
        System.out.println("Successfully signed up as " + message.getMainData().getUser().getUserName() + ".");
        Platform.runLater(() ->
        {
            Application.getInstance().loadSceneMainScreen();
        });
        Application.getInstance().getController().setWaitingForResponse(false);
    }

    public void receiveLogOutSuccessMessage()
    {
        System.out.println("Successfully logged out.");
        Platform.runLater(() ->
        {
            Application.getInstance().loadSceneLogin();
        });
        Application.getInstance().getController().setWaitingForResponse(false);
    }

    public void receiveNewGameSuccessMessage(NewGameSuccessMessage message)
    {
        System.out.println("Successfully started a new game.");
        Platform.runLater(() ->
        {
            Application.getInstance().loadSceneEditMaze();
        });
        Application.getInstance().getController().setWaitingForResponse(false);
    }

    public void receiveLeaveGameSuccessMessage(LeaveGameSuccessMessage message)
    {
        System.out.println("Successfully left the game.");
        Application.getInstance().getController().setGame(null);
        Platform.runLater(() ->
        {
            Application.getInstance().loadSceneMainScreen();
        });
        Application.getInstance().getController().setWaitingForResponse(false);
    }
}
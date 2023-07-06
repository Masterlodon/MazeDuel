package maze.server;

import maze.database.DBFunctions;
import maze.database.DBUser;
import maze.database.data.MainData;
import maze.database.data.User;
import maze.game.Game;
import maze.message.*;

import java.io.*;
import java.net.Socket;

public class ClientProxy implements Runnable
{
    private boolean exit;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private User user;

    public Socket getSocket()
    {
        return socket;
    }

    public User getUser()
    {
        return user;
    }

    public ClientProxy(Socket socket)
    {
        exit = false;
        this.socket = socket;
        try
        {
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
            in = new ObjectInputStream(socket.getInputStream());
            while((o = (Message) in.readObject()) != null && !exit)
            {
                if(o.getType() == LoginRequestMessage.class)
                {
                    receiveLoginRequestMessage((LoginRequestMessage) o);
                }
                else if(o.getType() == SignUpRequestMessage.class)
                {
                    receiveSignUpRequestMessage((SignUpRequestMessage) o);
                }
                else if(o.getType() == LogOutRequestMessage.class)
                {
                    receiveLogOutRequestMessage((LogOutRequestMessage) o);
                }
                else if(o.getType() == NewGameRequestMessage.class)
                {
                    receiveNewGameRequestMessage((NewGameRequestMessage) o);
                }
                else if(o.getType() == LeaveGameRequestMessage.class)
                {
                    receiveLeaveGameRequestMessage((LeaveGameRequestMessage) o);
                }
                else
                {
                    System.err.println("Unrecognizable message detected.\n" + o);
                }
            }
        }
        catch (Exception e)
        {
            if(user != null)
            {
                System.err.println("Connection to " + user.getUserName() + " was lost.");
                if(user.getGame() != null)
                {
                    user.getGame().removeCompetitor(user);
                    Server.getInstance().removeEmptyGames();
                }
                //Display games
                Server.getInstance().displayGames();
            }
            else
            {
                System.err.println("Connection on " + socket.toString() + " was lost.");
            }
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

    public void receiveLoginRequestMessage(LoginRequestMessage message)
    {
        int loginCheck = DBUser.checkLoginData(message.getUserName(), message.getPasswordHash());
        if(loginCheck == -1)
        {
            System.out.println(message.getUserName() + " tried to log in with the wrong password.");
            send(new LoginFailedMessage("The password is incorrect."));
        }
        else if(loginCheck == 0)
        {
            System.out.println("Someone tried to log in as the non existing user " + message.getUserName() + ".");
            send(new LoginFailedMessage("There is no user with the username " + message.getUserName() + "."));
        }
        else
        {
            System.out.println(message.getUserName() + " successfully logged in.");
            user = DBUser.getUserByUserName(message.getUserName());
            user.setClient(this);
            send(new LoginSuccessMessage(new MainData(user)));
        }
    }

    public void receiveSignUpRequestMessage(SignUpRequestMessage message)
    {
        if(DBUser.addUserToDB(message.getUserName(), message.getPasswordHash()))
        {
            DBFunctions.commit();
            System.out.println(message.getUserName() + " successfully created their account.");
            user = DBUser.getUserByUserName(message.getUserName());
            user.setClient(this);
            send(new SignUpSuccessMessage(new MainData(user)));
        }
        else
        {
            System.out.println("Someone tried to sign up with username " + message.getUserName() + ".");
            send(new SignUpFailedMessage(message.getUserName() + " is already taken."));
        }
    }

    public void receiveLogOutRequestMessage(LogOutRequestMessage message)
    {
        System.out.println(message.getUser().getUserName() + " logged out.");
        Server.getInstance().removeClient(this);
        send(new LogOutSuccessMessage());
        try
        {
            socket.close();
        }
        catch (Exception e)
        {
            System.err.println("Error in clientProxy.receiveLogOutRequestMessage.");
            e.printStackTrace();
        }
    }

    public void receiveNewGameRequestMessage(NewGameRequestMessage message)
    {
        user.setGame(new Game(user, message.getWidth(), message.getHeight(), message.isPublicGame()));
        Server.getInstance().addGame(user.getGame());
        //Display games
        Server.getInstance().displayGames();
        send(new NewGameSuccessMessage());
    }

    public void receiveLeaveGameRequestMessage(LeaveGameRequestMessage message)
    {
        user.getGame().removeCompetitor(user);
        Server.getInstance().removeEmptyGames();
        //Display games
        Server.getInstance().displayGames();
        send(new LeaveGameSuccessMessage());
    }
}

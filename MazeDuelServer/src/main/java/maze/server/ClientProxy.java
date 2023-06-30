package maze.server;

import maze.database.AccessConnection;
import maze.database.DBFunctions;
import maze.database.DBUser;
import maze.database.data.User;
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
            User user = DBUser.getUserByUserName(message.getUserName());
            this.user = user;
            send(new LoginSuccessMessage(user));
        }
    }

    public void receiveSignUpRequestMessage(SignUpRequestMessage message)
    {
        if(DBUser.addUserToDB(message.getUserName(), message.getPasswordHash()))
        {
            DBFunctions.commit();
            System.out.println(message.getUserName() + " successfully created their account.");
            User user = DBUser.getUserByUserName(message.getUserName());
            this.user = user;
            send(new SignUpSuccessMessage(user));
        }
        else
        {
            System.out.println("Someone tried to sign up with username " + message.getUserName() + ".");
            send(new SignUpFailedMessage(message.getUserName() + " is already taken."));
        }
    }
}

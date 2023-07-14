package maze.server;

import maze.database.DBFriendRequest;
import maze.database.DBFunctions;
import maze.database.DBUser;
import maze.database.data.MainData;
import maze.database.data.User;
import maze.game.Competitor;
import maze.game.Game;
import maze.game.GameJoinInfo;
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
                else if(o.getType() == RequestGamesMessage.class)
                {
                    receiveRequestGamesMessage((RequestGamesMessage) o);
                }
                else if(o.getType() == ActivateInfoUpdatesMessage.class)
                {
                    receiveActivateInfoUpdatesMessage((ActivateInfoUpdatesMessage) o);
                }
                else if(o.getType() == DeactivateInfoUpdatesMessage.class)
                {
                    receiveDeactivateInfoUpdatesMessage((DeactivateInfoUpdatesMessage) o);
                }
                else if(o.getType() == JoinGameRequestMessage.class)
                {
                    receiveJoinGameRequestMessage((JoinGameRequestMessage) o);
                }
                else if(o.getType() == GetPlayersRequestMessage.class)
                {
                    receiveGetPlayersRequestMessage((GetPlayersRequestMessage) o);
                }
                else if(o.getType() == FriendRequestRequestMessage.class)
                {
                    receiveFriendRequestRequestMessage((FriendRequestRequestMessage) o);
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
                user.setOnline(false);
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
        else if(loginCheck == -2)
        {
            System.out.println(message.getUserName() + " tried to log in while being already logged in.");
            send(new LoginFailedMessage(message.getUserName() + " is already logged in."));
        }
        else
        {
            System.out.println(message.getUserName() + " successfully logged in.");
            user = DBUser.getUserByUserName(message.getUserName());
            user.setClient(this);
            user.setOnline(true);
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
            user.setOnline(true);
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
        user.setOnline(false);
        user = null;
        send(new LogOutSuccessMessage());
    }

    public void receiveNewGameRequestMessage(NewGameRequestMessage message)
    {
        user.setGame(new Game(user, message.getWidth(), message.getHeight(), message.isPublicGame()));
        Server.getInstance().addGame(user.getGame());
        send(new NewGameSuccessMessage());
    }

    public void receiveLeaveGameRequestMessage(LeaveGameRequestMessage message)
    {
        user.getGame().removeCompetitor(user);
        Broadcaster.broadcast(DBUser.getUsersInGame(user.getGame(), user), new UpdateCompetitorsMessage(user.getGame()));
        user.setGame(null);
        user.setCompetitor(null);
        Server.getInstance().removeEmptyGames();
        send(new LeaveGameSuccessMessage());
    }

    public void receiveRequestGamesMessage(RequestGamesMessage message)
    {
        user.setInfoRequestType(message.getRequestType());
        if(message.getRequestType() == 'p')
        {
            System.out.println(user.getUserName() + " send a request for public games.");
            send(new SendGamesMessage(GameJoinInfo.getPublicInfo()));
        }
        else if(message.getRequestType() == 'f')
        {
            System.out.println(user.getUserName() + " send a request for friends games.");
            send(new SendGamesMessage(GameJoinInfo.getFriendsInfo(user)));
        }
    }

    public void receiveActivateInfoUpdatesMessage(ActivateInfoUpdatesMessage message)
    {
        System.out.println(user.getUserName() + " activated info updates.");
        user.setInfoUpdates(true);
        send(new ActivateInfoUpdatesMessage());
    }

    public void receiveDeactivateInfoUpdatesMessage(DeactivateInfoUpdatesMessage message)
    {
        System.out.println(user.getUserName() + " deactivated info updates.");
        user.setInfoUpdates(false);
    }

    public void receiveJoinGameRequestMessage(JoinGameRequestMessage message)
    {
        User host = DBUser.getUserById(message.getHostId());
        if(host != null)
        {
            user.setCompetitor(new Competitor(user, host.getGame().getWidth(), host.getGame().getHeight()));
            host.getGame().addCompetitor(user.getCompetitor());
            user.setGame(host.getGame());
            System.out.println("Host: " + user.getGame().getHost().toString());
            send(new JoinGameSuccessMessage(user.getGame()));
            Broadcaster.broadcast(DBUser.getUsersInGame(user.getGame(), user), new UpdateCompetitorsMessage(user.getGame()));
        }
        else
        {
            System.err.println(user.getUserName() + " tried to join a non existing game.");
        }
    }

    public void receiveGetPlayersRequestMessage(GetPlayersRequestMessage message)
    {
        System.out.println(user.getUserName() + " requested to get all users.");
        send(new GetPlayersSuccessMessage(MainData.getInstance().getUsers()));
    }

    public void receiveFriendRequestRequestMessage(FriendRequestRequestMessage message)
    {
        if(user.hasFriendRequestWith(message.getUser()))
        {
            send(new FriendRequestFailedMessage("Can not send friend request since this user already received a friend request from you or send a friend request to you."));
        }
        else if(user.isFriendsWith(message.getUser()))
        {
            send(new FriendRequestFailedMessage("Can not send friend request since this user is already your friend."));
        }
        else if(user.getId() == message.getUser().getId())
        {
            send(new FriendRequestFailedMessage("Can not send friend request to your self."));
        }
        else
        {
            DBFriendRequest.addFriendRequestToDB(user, message.getUser());
            send(new FriendRequestSuccessMessage(DBFriendRequest.getFriendRequestByFriends(user, message.getUser()), DBUser.getUserById(message.getUser().getId())));
            DBFunctions.commit();
            DBUser.getUserById(message.getUser().getId()).getClient().send(new FriendRequestMessage(DBFriendRequest.getFriendRequestByFriends(user, message.getUser()), user));
        }
    }
}

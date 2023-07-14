package maze.client;

import gui.mazeduelclient.Application;
import javafx.application.Platform;
import maze.database.DBUser;
import maze.database.data.Friendship;
import maze.database.data.MainData;
import maze.game.Competitor;
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
                else if(o.getType() == SendGamesMessage.class)
                {
                    receiveSendGamesMessage((SendGamesMessage) o);
                }
                else if(o.getType() == ActivateInfoUpdatesMessage.class)
                {
                    receiveActivateInfoUpdatesMessage((ActivateInfoUpdatesMessage) o);
                }
                else if(o.getType() == JoinGameSuccessMessage.class)
                {
                    receiveJoinGameSuccessMessage((JoinGameSuccessMessage) o);
                }
                else if(o.getType() == UpdateCompetitorsMessage.class)
                {
                    receiveUpdateCompetitorsMessage((UpdateCompetitorsMessage) o);
                }
                else if(o.getType() == FriendJoinedMessage.class)
                {
                    receiveFriendJoinedMessage((FriendJoinedMessage) o);
                }
                else if(o.getType() == FriendLeftMessage.class)
                {
                    receiveFriendLeftMessage((FriendLeftMessage) o);
                }
                else if(o.getType() == GetPlayersSuccessMessage.class)
                {
                    receiveGetPlayersSuccessMessage((GetPlayersSuccessMessage) o);
                }
                else if(o.getType() == FriendRequestFailedMessage.class)
                {
                    receiveFriendRequestFailedMessage((FriendRequestFailedMessage) o);
                }
                else if(o.getType() == FriendRequestSuccessMessage.class)
                {
                    receiveFriendRequestSuccessMessage((FriendRequestSuccessMessage) o);
                }
                else if(o.getType() == FriendRequestMessage.class)
                {
                    receiveFriendRequestMessage((FriendRequestMessage) o);
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

    public void receiveSendGamesMessage(SendGamesMessage message)
    {
        System.out.println("Received game info.");
        Platform.runLater(() ->
        {
            Application.getInstance().getController().getJoinGameController().loadGames(message.getInfo());
        });
        Application.getInstance().getController().setWaitingForResponse(false);
    }

    public void receiveActivateInfoUpdatesMessage(ActivateInfoUpdatesMessage message)
    {
        Platform.runLater(() ->
        {
            Application.getInstance().finishLoadSceneJoinGame();
        });
    }

    public void receiveJoinGameSuccessMessage(JoinGameSuccessMessage message)
    {
        message.getGame().fillReferences(MainData.getInstance().getUser());
        System.out.println("Successfully joined " + message.getGame().getHost().getUser().getUserName() + "'s game.");
        Application.getInstance().getController().setGame(message.getGame());
        for(Competitor competitor : message.getGame().getCompetitors())
        {
            if(competitor.getUser().getId() == MainData.getInstance().getUser().getId())
            {
                Application.getInstance().getController().setCompetitor(competitor);
            }
        }
        Platform.runLater(() ->
        {
            Application.getInstance().loadSceneEditMaze();
        });
        Application.getInstance().getController().setWaitingForResponse(false);
        send(new DeactivateInfoUpdatesMessage());
    }

    public void receiveUpdateCompetitorsMessage(UpdateCompetitorsMessage message)
    {
        message.getGame().fillReferences(MainData.getInstance().getUser());
        Platform.runLater(() ->
        {
            Application.getInstance().getController().getEditMazeController().loadCompetitors(message.getGame());
        });
    }

    public void receiveFriendJoinedMessage(FriendJoinedMessage message)
    {
        DBUser.getUserById(message.getUserId()).setOnline(true);
    }

    public void receiveFriendLeftMessage(FriendLeftMessage message)
    {
        DBUser.getUserById(message.getUserId()).setOnline(false);
    }

    public void receiveGetPlayersSuccessMessage(GetPlayersSuccessMessage message)
    {
        System.out.println("Received all users from server.");
        Application.getInstance().getController().getAddFriendController().setUsers(message.getUsers());
        Platform.runLater(() ->
        {
            Application.getInstance().finishLoadSceneAddFriend();
        });
    }

    public void receiveFriendRequestFailedMessage(FriendRequestFailedMessage message)
    {
        System.out.println(message.getContents());
        Application.getInstance().getController().setWaitingForResponse(false);
    }

    public void receiveFriendRequestSuccessMessage(FriendRequestSuccessMessage message)
    {
        System.out.println("Successfully send the friend request.");
        MainData.getInstance().getUsers().add(message.getReceiver());
        MainData.getInstance().getFriendRequests().add(message.getFriendRequest());
        Platform.runLater(() ->
        {
            Application.getInstance().getController().getAddFriendController().updateSendRequestCount();
        });
        Application.getInstance().getController().setWaitingForResponse(false);
    }

    public void receiveFriendRequestMessage(FriendRequestMessage message)
    {
        MainData.getInstance().getUsers().add(message.getSender());
        MainData.getInstance().getFriendRequests().add(message.getFriendRequest());
        Platform.runLater(() ->
        {
            Application.getInstance().getController().getAddFriendController().updateReceivedRequestCount();
        });
    }
}
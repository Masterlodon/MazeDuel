package gui.mazeduelclient;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import maze.client.ServerProxy;
import maze.message.ActivateInfoUpdatesMessage;
import maze.message.GetPlayersRequestMessage;

import java.io.IOException;

public class Application extends javafx.application.Application
{
    private static Application instance;
    private Scene sceneLogin;
    private Scene sceneSignUp;
    private Scene sceneMainScreen;
    private Scene sceneNewGame;
    private Scene sceneEditMaze;
    private Scene sceneJoinGame;
    private Scene sceneFriends;
    private Scene sceneAddFriend;
    private Stage stage;
    private ServerProxy server;
    private Controller controller;
    private String activeScene;

    @Override
    public void start(Stage stage) throws IOException
    {
        //Set close event
        stage.setOnCloseRequest(new EventHandler<WindowEvent>()
        {
            @Override
            public void handle(WindowEvent t)
            {
                Platform.exit();
                System.exit(0);
            }
        });

        instance = this;
        controller = new Controller();
        server = new ServerProxy();
        FXMLLoader fxmlLoader;
        this.stage = stage;

        fxmlLoader = new FXMLLoader(Application.class.getResource("login-view.fxml"));
        sceneLogin = new Scene(fxmlLoader.load(), 720, 480);
        fxmlLoader = new FXMLLoader(Application.class.getResource("signUp-view.fxml"));
        sceneSignUp = new Scene(fxmlLoader.load(), 720, 480);
        fxmlLoader = new FXMLLoader(Application.class.getResource("mainScreen-view.fxml"));
        sceneMainScreen = new Scene(fxmlLoader.load(), 720, 480);
        fxmlLoader = new FXMLLoader(Application.class.getResource("newGame-view.fxml"));
        sceneNewGame = new Scene(fxmlLoader.load(), 720, 480);
        fxmlLoader = new FXMLLoader(Application.class.getResource("editMaze-view.fxml"));
        sceneEditMaze = new Scene(fxmlLoader.load(), 720, 480);
        fxmlLoader = new FXMLLoader(Application.class.getResource("joinGame-view.fxml"));
        sceneJoinGame = new Scene(fxmlLoader.load(), 720, 480);
        fxmlLoader = new FXMLLoader(Application.class.getResource("friends-view.fxml"));
        sceneFriends = new Scene(fxmlLoader.load(), 720, 480);
        fxmlLoader = new FXMLLoader(Application.class.getResource("addFriend-view.fxml"));
        sceneAddFriend = new Scene(fxmlLoader.load(), 720, 480);

        loadSceneLogin();
    }

    public void loadSceneLogin()
    {
        activeScene = "login";
        stage.setTitle("Login");
        stage.setScene(sceneLogin);
        stage.show();
    }

    public void loadSceneSignUp()
    {
        activeScene = "signUp";
        stage.setTitle("Sign up");
        stage.setScene(sceneSignUp);
        stage.show();
    }

    public void loadSceneMainScreen()
    {
        activeScene = "mainScreen";
        stage.setTitle("Main Screen");
        stage.setScene(sceneMainScreen);
        stage.show();
    }

    public void loadSceneNewGame()
    {
        activeScene = "newGame";
        stage.setTitle("New Game");
        stage.setScene(sceneNewGame);
        stage.show();
    }

    public void loadSceneEditMaze()
    {
        activeScene = "editMaze";
        controller.getEditMazeController().loadCompetitors(controller.getGame());
        controller.getGame().getPlayer().getMaze().adjustGridPane(controller.getEditMazeController().getGridPaneMaze());
        stage.setTitle("Edit Maze");
        stage.setScene(sceneEditMaze);
        stage.show();
    }

    public void loadSceneJoinGame()
    {
        controller.setWaitingForResponse(true);
        server.send(new ActivateInfoUpdatesMessage());
    }

    public void finishLoadSceneJoinGame()
    {
        activeScene = "joinGame";
        controller.getJoinGameController().loadGames();
        stage.setTitle("Join Game");
        stage.setScene(sceneJoinGame);
        stage.show();
        controller.setWaitingForResponse(false);
    }

    public void loadSceneFriends()
    {
        activeScene = "friends";
        controller.getFriendsController().loadFriends();
        stage.setTitle("Friends");
        stage.setScene(sceneFriends);
        stage.show();
    }

    public void loadSceneAddFriend()
    {
        controller.setWaitingForResponse(true);
        server.send(new GetPlayersRequestMessage());
    }

    public void finishLoadSceneAddFriend()
    {
        activeScene = "addFriend";
        controller.getAddFriendController().loadUsers();
        stage.setTitle("Add Friend");
        stage.setScene(sceneAddFriend);
        stage.show();
        controller.setWaitingForResponse(false);
    }

    public static Application getInstance()
    {
        return instance;
    }

    public ServerProxy getServer()
    {
        return server;
    }

    public Controller getController()
    {
        return controller;
    }

    public String getActiveScene()
    {
        return activeScene;
    }

    public static void main(String[] args)
    {
        launch();
    }
}
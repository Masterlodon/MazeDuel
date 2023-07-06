package gui.mazeduelclient;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import maze.client.ServerProxy;

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
    private Stage stage;
    private ServerProxy server;
    private Controller controller;

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

        loadSceneLogin();
    }

    public void loadSceneLogin()
    {
        stage.setTitle("Login");
        stage.setScene(sceneLogin);
        stage.show();
    }

    public void loadSceneSignUp()
    {
        stage.setTitle("Sign up");
        stage.setScene(sceneSignUp);
        stage.show();
    }

    public void loadSceneMainScreen()
    {
        stage.setTitle("Main Screen");
        stage.setScene(sceneMainScreen);
        stage.show();
    }

    public void loadSceneNewGame()
    {
        stage.setTitle("New Game");
        stage.setScene(sceneNewGame);
        stage.show();
    }

    public void loadSceneEditMaze()
    {
        stage.setTitle("Edit Maze");
        stage.setScene(sceneEditMaze);
        stage.show();
    }

    public void loadSceneJoinGame()
    {
        stage.setTitle("Join Game");
        stage.setScene(sceneJoinGame);
        stage.show();
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

    public static void main(String[] args)
    {
        launch();
    }


}
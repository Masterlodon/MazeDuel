package gui.mazeduelclient;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application
{
    private static Application instance;
    private static Scene sceneLogin;
    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException
    {
        instance = this;
        FXMLLoader fxmlLoader;
        Application.stage = stage;

        fxmlLoader = new FXMLLoader(Application.class.getResource("login-view.fxml"));
        sceneLogin = new Scene(fxmlLoader.load(), 720, 480);

        loadSceneLogin();
    }

    public static void loadSceneLogin()
    {
        stage.setTitle("Login");
        stage.setScene(sceneLogin);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}
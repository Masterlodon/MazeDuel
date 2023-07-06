package gui.mazeduelclient;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class JoinGameController implements Initializable
{
    @FXML
    private Button buttonFriends;
    @FXML
    private Button buttonPublic;
    @FXML
    private ListView listViewGames;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        Application.getInstance().getController().registerJoinGameController(this);
    }

    @FXML
    private void goBack()
    {
        Application.getInstance().loadSceneMainScreen();
    }

    @FXML
    private void selectFriends()
    {
        buttonFriends.setDisable(true);
        buttonPublic.setDisable(false);
    }

    @FXML
    private void selectPublic()
    {
        buttonPublic.setDisable(true);
        buttonFriends.setDisable(false);
    }

    @FXML
    private void joinGame()
    {

    }
}

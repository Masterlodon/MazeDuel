package gui.mazeduelclient;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import maze.database.data.MainData;
import maze.message.LogOutRequestMessage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable
{
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        Application.getInstance().getController().registerMainScreenController(this);
    }

    @FXML
    private void logOut()
    {
        if(Application.getInstance().getController().isWaitingForResponse() == false)
        {
            Application.getInstance().getServer().send(new LogOutRequestMessage(MainData.getInstance().getUser()));
            Application.getInstance().getController().setWaitingForResponse(true);
        }
        else
        {
            System.out.println("Can not log out while waiting for server response.");
        }
    }

    @FXML
    private void newGame()
    {
        Application.getInstance().loadSceneNewGame();
    }

    @FXML
    private void joinGame()
    {
        Application.getInstance().loadSceneJoinGame();
    }
}

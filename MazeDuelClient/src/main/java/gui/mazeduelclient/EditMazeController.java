package gui.mazeduelclient;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import maze.message.LeaveGameRequestMessage;

import java.net.URL;
import java.util.ResourceBundle;

public class EditMazeController implements Initializable
{

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        Application.getInstance().getController().registerEditMazeController(this);
    }

    @FXML
    private void leaveGame()
    {
        if(Application.getInstance().getController().isWaitingForResponse() == false)
        {
            Application.getInstance().getController().setWaitingForResponse(true);
            Application.getInstance().getServer().send(new LeaveGameRequestMessage());
        }
        else
        {
            System.out.println("Can not leave game while waiting for server response.");
        }
    }
}

package gui.mazeduelclient;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable
{
    private boolean waitingForResponse;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        waitingForResponse = false;
    }

    public void setWaitingForResponse(boolean waitingForResponse)
    {
        this.waitingForResponse = waitingForResponse;
    }

    @FXML
    private void logOut()
    {
        
    }
}

package gui.mazeduelclient;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import maze.message.LoginRequestMessage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable
{
    boolean waitingForResponse;
    @FXML
    private TextField textFieldUserName;
    @FXML
    private TextField textFieldPassword;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        Application.getInstance().getController().registerLoginController(this);
        waitingForResponse = false;
    }

    public void setWaitingForResponse(boolean waitingForConfirmation)
    {
        this.waitingForResponse = waitingForConfirmation;
    }

    @FXML
    private void login()
    {
        if(textFieldUserName.getText().length() > 0 && textFieldPassword.getText().length() > 0)
        {
            if(waitingForResponse == false)
            {
                Application.getInstance().getServer().send(new LoginRequestMessage(textFieldUserName.getText(), textFieldPassword.getText().hashCode()));
                waitingForResponse = true;
            }
            else
            {
                System.out.println("Can not login while waiting for server response.");
            }
        }
        else
        {
            System.out.println("Please provide username and password to login.");
        }
    }
    @FXML
    private void loadSceneSignUp()
    {
        Application.getInstance().loadSceneSignUp();
    }
}
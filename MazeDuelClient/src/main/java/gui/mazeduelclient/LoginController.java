package gui.mazeduelclient;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import maze.message.LoginRequestMessage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable
{
    @FXML
    private TextField textFieldUserName;
    @FXML
    private PasswordField passwordFieldInput;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        Application.getInstance().getController().registerLoginController(this);
    }

    @FXML
    private void login()
    {
        if(textFieldUserName.getText().length() > 0 && passwordFieldInput.getText().length() > 0)
        {
            if(Application.getInstance().getController().isWaitingForResponse() == false)
            {
                Application.getInstance().getServer().send(new LoginRequestMessage(textFieldUserName.getText(), passwordFieldInput.getText().hashCode()));
                Application.getInstance().getController().setWaitingForResponse(true);
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
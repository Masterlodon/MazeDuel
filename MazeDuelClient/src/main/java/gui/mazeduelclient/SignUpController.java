package gui.mazeduelclient;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import maze.message.SignUpRequestMessage;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable
{
    @FXML
    private TextField textFieldUserName;
    @FXML
    private PasswordField passwordFieldInput;
    @FXML
    private PasswordField passwordFieldConfirm;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        Application.getInstance().getController().registerSignUpController(this);
    }

    @FXML
    private void signUp()
    {
        if(Application.getInstance().getController().isWaitingForResponse() == false)
        {
            if(textFieldUserName.getText().length() > 0)
            {
                if(passwordFieldInput.getText().length() > 0)
                {
                    if(passwordFieldConfirm.getText().length() > 0)
                    {
                        if(passwordFieldInput.getText().equals(passwordFieldConfirm.getText()))
                        {
                            Application.getInstance().getServer().send(new SignUpRequestMessage(textFieldUserName.getText(), passwordFieldInput.getText().hashCode()));
                            Application.getInstance().getController().setWaitingForResponse(true);
                        }
                        else
                        {
                            System.out.println("Password does not match confirm password.");
                        }
                    }
                    else
                    {
                        System.out.println("Confirm Password is an obligatory field.");
                    }
                }
                else
                {
                    System.out.println("Password is an obligatory field.");
                }
            }
            else
            {
                System.out.println("No username specified.");
            }
        }
        else
        {
            System.out.println("Can not sign up while waiting for server response.");
        }
    }

    @FXML
    private void loadSceneLogin()
    {
        Application.getInstance().loadSceneLogin();
    }
}

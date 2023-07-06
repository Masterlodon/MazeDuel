package gui.mazeduelclient;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import maze.database.data.MainData;
import maze.game.Game;
import maze.message.NewGameRequestMessage;

import java.net.URL;
import java.util.ResourceBundle;

public class NewGameController implements Initializable
{
    @FXML
    private TextField textFieldWidth;
    @FXML
    private TextField textFieldHeight;
    @FXML
    private Slider sliderWidth;
    @FXML
    private Slider sliderHeight;
    @FXML
    private CheckBox checkBoxPublic;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        Application.getInstance().getController().registerNewGameController(this);
        converter();
    }

    private void converter()
    {
        sliderWidth.valueProperty().addListener((observable, oldValue, newValue) ->
        {
            textFieldWidth.setText(String.valueOf(newValue));
        });

        sliderHeight.valueProperty().addListener((observable, oldValue, newValue) ->
        {
            textFieldHeight.setText(String.valueOf(newValue));
        });
    }

    @FXML
    private void goBack()
    {
        Application.getInstance().loadSceneMainScreen();
    }

    @FXML
    private void startGame()
    {
        if(Application.getInstance().getController().isWaitingForResponse() == false)
        {
            Application.getInstance().getController().setWaitingForResponse(true);
            Game game = new Game(MainData.getInstance().getUser(), Integer.valueOf(textFieldWidth.getText()), Integer.valueOf(textFieldHeight.getText()), checkBoxPublic.isSelected());
            Application.getInstance().getController().setGame(game);
            Application.getInstance().getServer().send(new NewGameRequestMessage(game.getWidth(), game.getHeight(), game.isPublicGame()));
        }
        else
        {
            System.out.println("Can not start new game while waiting for server response.");
        }
    }
}

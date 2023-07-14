package gui.mazeduelclient;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import maze.game.GameJoinInfo;
import maze.message.DeactivateInfoUpdatesMessage;
import maze.message.JoinGameRequestMessage;
import maze.message.RequestGamesMessage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class JoinGameController implements Initializable
{
    private boolean showAll;
    @FXML
    private Button buttonFriends;
    @FXML
    private Button buttonPublic;
    @FXML
    private ListView listViewGames;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        showAll = false;
        Application.getInstance().getController().registerJoinGameController(this);
    }

    @FXML
    private void goBack()
    {
        Application.getInstance().getServer().send(new DeactivateInfoUpdatesMessage());
        Application.getInstance().loadSceneMainScreen();
    }

    @FXML
    private void selectFriends()
    {
        if(Application.getInstance().getController().isWaitingForResponse() == false)
        {
            showAll = false;
            buttonFriends.setDisable(true);
            buttonPublic.setDisable(false);
            loadGamesFriends();
        }
        else
        {
            System.out.println("Can not load friends games while waiting for server response.");
        }
    }

    @FXML
    private void selectPublic()
    {
        if(Application.getInstance().getController().isWaitingForResponse() == false)
        {
            showAll = true;
            buttonPublic.setDisable(true);
            buttonFriends.setDisable(false);
            loadGamesPublic();
        }
        else
        {
            System.out.println("Can not load public games while waiting for server response.");
        }
    }

    public void loadGames()
    {
        if(showAll)
        {
            loadGamesPublic();
        }
        else
        {
            loadGamesFriends();
        }
    }

    private void loadGamesFriends()
    {
        Application.getInstance().getController().setWaitingForResponse(true);
        Application.getInstance().getServer().send(new RequestGamesMessage('f'));
    }

    private void loadGamesPublic()
    {
        Application.getInstance().getController().setWaitingForResponse(true);
        Application.getInstance().getServer().send(new RequestGamesMessage('p'));
    }

    public void loadGames(ArrayList<GameJoinInfo> info)
    {
        listViewGames.getItems().clear();
        listViewGames.getItems().addAll(info);
    }

    @FXML
    private void joinGame()
    {
        if(listViewGames.getSelectionModel().getSelectedItem() != null)
        {
            if(Application.getInstance().getController().isWaitingForResponse() == false)
            {
                Application.getInstance().getController().setWaitingForResponse(true);
                Application.getInstance().getServer().send(new JoinGameRequestMessage(((GameJoinInfo) listViewGames.getSelectionModel().getSelectedItem()).getHostId()));
            }
            else
            {
                System.out.println("Can not join game while waiting for server response.");
            }
        }
        else
        {
            System.out.println("No Game selected.");
        }
    }
}

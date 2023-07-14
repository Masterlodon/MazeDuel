package gui.mazeduelclient;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import maze.database.DBUser;
import maze.database.data.MainData;

import java.net.URL;
import java.util.ResourceBundle;

public class FriendsController implements Initializable
{
    private boolean showAll;
    @FXML
    private ListView listViewFriends;
    @FXML
    private Button buttonOnline;
    @FXML
    private Button buttonAll;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        showAll = false;
        Application.getInstance().getController().registerFriendsController(this);
    }

    public void loadFriends()
    {
        listViewFriends.getItems().clear();
        if(showAll)
        {
            listViewFriends.getItems().addAll(DBUser.getFriendsByUser(MainData.getInstance().getUser()));
        }
        else
        {
            listViewFriends.getItems().addAll(DBUser.getOnlineFriendsByUser(MainData.getInstance().getUser()));
        }
    }

    @FXML
    private void loadOnline()
    {
        buttonOnline.setDisable(true);
        buttonAll.setDisable(false);
        showAll = !showAll;
        loadFriends();
    }

    @FXML
    private void loadAll()
    {
        buttonAll.setDisable(true);
        buttonOnline.setDisable(false);
        showAll = !showAll;
        loadFriends();
    }

    @FXML
    private void goBack()
    {
        Application.getInstance().loadSceneMainScreen();
    }

    @FXML
    private void addFriend()
    {
        if(Application.getInstance().getController().isWaitingForResponse() == false)
        {
            Application.getInstance().loadSceneAddFriend();
        }
        else
        {
            System.out.println("Can not add friend while waiting for server response.");
        }
    }
}

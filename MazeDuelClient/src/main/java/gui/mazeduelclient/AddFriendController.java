package gui.mazeduelclient;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import maze.database.data.MainData;
import maze.database.data.User;
import maze.message.FriendRequestRequestMessage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class AddFriendController implements Initializable
{
    private ArrayList<User> users;
    @FXML
    private ListView listViewUsers;
    @FXML
    private TextField textFieldSearch;
    @FXML
    private Label labelReceivedRequestCount;
    @FXML
    private Label labelSendRequestCount;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        Application.getInstance().getController().registerAddFriendController(this);
    }

    public void setUsers(ArrayList<User> users)
    {
        this.users = users;
    }

    public void updateReceivedRequestCount()
    {
        labelReceivedRequestCount.setText(String.valueOf(MainData.getInstance().getReceivedFriendRequests().size()));
    }

    public void updateSendRequestCount()
    {
        labelSendRequestCount.setText(String.valueOf(MainData.getInstance().getSendFriendRequests().size()));
    }

    @FXML
    private void goBack()
    {
        if(Application.getInstance().getController().isWaitingForResponse() == false)
        {
            Application.getInstance().loadSceneFriends();
        }
        else
        {
            System.out.println("Can not go back while waiting for server response.");
        }
    }

    @FXML
    public void loadUsers()
    {
        listViewUsers.getItems().clear();
        if(textFieldSearch.getText().length() > 0)
        {
            for(User user : users)
            {
                user.setMatch(false);
            }
            String[] words = textFieldSearch.getText().toLowerCase(Locale.ROOT).split(" ");
            for(User user : users)
            {
                if(user.isMatch() == false)
                {
                    boolean match = true;
                    for(String word : words)
                    {
                        if(user.getUserName().toLowerCase(Locale.ROOT).contains(word) == false)
                        {
                            match = false;
                            break;
                        }
                    }
                    user.setMatch(match);
                }
            }
            for(User user : users)
            {
                if(user.isMatch())
                {
                    listViewUsers.getItems().add(user);
                }
            }
        }
        else
        {
            listViewUsers.getItems().addAll(users);
        }
    }

    @FXML
    private void loadPendingRequests()
    {
        if(Application.getInstance().getController().isWaitingForResponse() == false)
        {

        }
        else
        {
            System.out.println("Can not add friend while waiting for server response.");
        }
    }

    @FXML
    private void addFriend()
    {
        if(Application.getInstance().getController().isWaitingForResponse() == false)
        {
            if(listViewUsers.getSelectionModel().getSelectedItem() != null)
            {
                Application.getInstance().getServer().send(new FriendRequestRequestMessage((User) listViewUsers.getSelectionModel().getSelectedItem()));
                Application.getInstance().getController().setWaitingForResponse(true);
            }
            else
            {
                System.out.println("No user selected.");
            }
        }
        else
        {
            System.out.println("Can not add friend while waiting for server response.");
        }
    }
}

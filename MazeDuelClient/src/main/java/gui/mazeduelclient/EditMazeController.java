package gui.mazeduelclient;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import maze.game.Competitor;
import maze.game.Game;
import maze.message.LeaveGameRequestMessage;

import java.net.URL;
import java.util.ResourceBundle;

public class EditMazeController implements Initializable
{
    @FXML
    private ListView listViewCompetitors;
    @FXML
    private GridPane gridPaneMaze;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        Application.getInstance().getController().registerEditMazeController(this);
    }

    public GridPane getGridPaneMaze()
    {
        return gridPaneMaze;
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

    public void loadCompetitors(Game game)
    {
        listViewCompetitors.getItems().clear();
        String[] competitors = new String[game.getCompetitors().size()];
        competitors[0] = "Host: " + game.getHost().getUser().getUserName();
        game.getCompetitors().remove(game.getHost());
        int i = 1;
        for(Competitor competitor : game.getCompetitors())
        {
            competitors[i] = "Player: " + competitor.getUser().getUserName();
            i++;
        }
        game.getCompetitors().add(game.getHost());
        listViewCompetitors.getItems().addAll(competitors);
    }
}

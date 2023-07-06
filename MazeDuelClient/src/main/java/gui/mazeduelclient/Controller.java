package gui.mazeduelclient;

import maze.game.Game;

public class Controller
{
    private boolean waitingForResponse;
    private Game game;
    private LoginController loginController;
    private SignUpController signUpController;
    private MainScreenController mainScreenController;
    private NewGameController newGameController;
    private EditMazeController editMazeController;
    private JoinGameController joinGameController;

    public Controller()
    {
        waitingForResponse = false;
    }

    public void registerLoginController(LoginController loginController)
    {
        this.loginController = loginController;
    }

    public void registerSignUpController(SignUpController signUpController)
    {
        this.signUpController = signUpController;
    }

    public void registerMainScreenController(MainScreenController mainScreenController)
    {
        this.mainScreenController = mainScreenController;
    }

    public void registerNewGameController(NewGameController newGameController)
    {
        this.newGameController = newGameController;
    }

    public void registerEditMazeController(EditMazeController editMazeController)
    {
        this.editMazeController = editMazeController;
    }

    public void registerJoinGameController(JoinGameController joinGameController)
    {
        this.joinGameController = joinGameController;
    }

    public LoginController getLoginController()
    {
        return loginController;
    }

    public SignUpController getSignUpController()
    {
        return signUpController;
    }

    public MainScreenController getMainScreenController()
    {
        return mainScreenController;
    }

    public NewGameController getNewGameController()
    {
        return newGameController;
    }

    public EditMazeController getEditMazeController()
    {
        return editMazeController;
    }

    public JoinGameController getJoinGameController()
    {
        return joinGameController;
    }

    public boolean isWaitingForResponse()
    {
        return waitingForResponse;
    }

    public void setWaitingForResponse(boolean waitingForResponse)
    {
        this.waitingForResponse = waitingForResponse;
    }

    public Game getGame()
    {
        return game;
    }

    public void setGame(Game game)
    {
        this.game = game;
    }
}

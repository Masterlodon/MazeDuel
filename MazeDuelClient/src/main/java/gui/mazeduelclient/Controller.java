package gui.mazeduelclient;

public class Controller
{
    private LoginController loginController;
    private SignUpController signUpController;
    private MainScreenController mainScreenController;

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
}

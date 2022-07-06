package app.dbmanagement;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AuthorizationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button loginInButton;

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField password_field;

    @FXML
    private Button signup;

    @FXML
    void initialize() {
//        assert loginInButton != null : "fx:id=\"loginInButton\" was not injected: check your FXML file 'authorization.fxml'.";
//        assert login_field != null : "fx:id=\"login_field\" was not injected: check your FXML file 'authorization.fxml'.";
//        assert password_field != null : "fx:id=\"password_field\" was not injected: check your FXML file 'authorization.fxml'.";
//        assert signup != null : "fx:id=\"signup\" was not injected: check your FXML file 'authorization.fxml'.";

        loginInButton.setOnAction(event ->{
            System.out.println("Войти");
        });
    }

}

package app.dbmanagement;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField password_field;

    @FXML
    private Button signUpButton;

    @FXML
    private CheckBox signUpFemale;

    @FXML
    private TextField signUpLastName;

    @FXML
    private TextField signUpMail;

    @FXML
    private CheckBox signUpMale;

    @FXML
    private TextField signUpName;

    @FXML
    void initialize() {


    }

}

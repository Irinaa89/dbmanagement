package app.dbmanagement;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button signUpBack;

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField password_field;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField signUpGroup;

    @FXML
    private TextField signUpRank;

    @FXML
    void initialize() {
        signUpBack.setOnAction(actionEvent -> {
            signUpButton.getScene().getWindow().hide();

            Stage stage = new Stage();

            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource( "authorization.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.setScene(new Scene(root));
            stage.setTitle("Authorization");
            stage.show();

        });

        signUpButton.setOnAction(event ->{
            signUPNewUser();
        });
    }

    private void signUPNewUser() {
        DatabaseHandler dbHandler = new DatabaseHandler();

        String login = login_field.getText();
        String password = password_field.getText();
        String group = signUpGroup.getText();

        User user = new User(login, password, group);

        try {
            dbHandler.signUpUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
package app.dbmanagement;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
        loginInButton.setOnAction(event ->{
            System.out.println("Войти");
        });

        signup.setOnAction(event ->{
            signup.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            //loader.setLocation(getClass().getResource("/app.dbmanagement/SignUP.fxml"));
            System.out.println(getClass().getResource("SignUP.fxml"));
            loader.setLocation(getClass().getResource("SignUP.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        });
    }

}

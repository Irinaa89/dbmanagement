package app.dbmanagement;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
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
            String loginText = login_field.getText().trim();
            String loginPassword = password_field.getText().trim();

            if (!loginText.equals("") && !loginPassword.equals("")) {
                try {
                    loginUser(loginText, loginPassword);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else
                System.out.println("Login and password is empty");
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

    private void loginUser(String loginText, String loginPassword) throws SQLException, ClassNotFoundException {
        DatabaseHandler dbHandler = new DatabaseHandler();
        User user = new User();
        user.setLogin(loginText);
        user.setPassword(loginPassword);
        ResultSet result = dbHandler.getUser(user);

        int counter = 0;
        while (result.next()){
            counter++;
        }

        if(counter >= 1) {
            signup.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            //loader.setLocation(getClass().getResource("/app.dbmanagement/SignUP.fxml"));
            System.out.println(getClass().getResource("app.fxml"));
            loader.setLocation(getClass().getResource("app.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        }
        else {
            Animation userLoginAnim = new Animation(login_field);
            Animation userPassAnim = new Animation(password_field);
            userLoginAnim.playAnim();
            userPassAnim.playAnim();
        }
    }
}

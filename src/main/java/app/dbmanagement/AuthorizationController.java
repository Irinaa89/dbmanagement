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
import javafx.scene.control.Alert;
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
    private Button guest;

    @FXML
    private Button signup;

    public static User user = new User();

    @FXML
    void initialize() {

        guest.setOnAction(actionEvent -> {
            try {
                user.setRank("guest");
                changeScene("app", "Главная страница (Гость)");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        loginInButton.setOnAction(event ->{
            String loginText = login_field.getText().trim();
            String loginPassword = password_field.getText().trim();

            if (!loginText.equals("") && !loginPassword.equals("")) {
                try {
                    loginUser(loginText, loginPassword);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Поля не могут быть пустыми.");
                alert.showAndWait();
            }
        });

        signup.setOnAction(actionEvent -> {
            try {
                changeScene("signUP", "Регистрация");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void loginUser(String loginText, String loginPassword) throws SQLException, ClassNotFoundException, IOException {
        DatabaseHandler dbHandler = new DatabaseHandler();
        User user = new User();
        user.setLogin(loginText);
        user.setPassword(loginPassword);
        ResultSet result = dbHandler.getUser(user);


        if (result.next()) {
            do {
                this.user = user;
                user.setId(result.getInt(1));
                user.setStudyGroup(result.getString(4));
                user.setRank(result.getString(5));

                if (user.getRank().equals("admin")) {
                    changeScene("app", "Главная страница (Администратор)");
                } else if (user.getRank().equals("user")) {
                    changeScene("app", "Главная страница (Пользователь)");
                } else if (user.getRank().equals("elder")) {
                    changeScene("app", "Главная страница (Староста)");
                }
            } while(result.next());
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Пользователь не найден");
            alert.showAndWait();
        }
    }

    public void changeScene(String fileName, String sceneName) throws IOException {
        signup.getScene().getWindow().hide();
        Stage stage = new Stage();

        Parent root = FXMLLoader.load(getClass().getResource(fileName + ".fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle(sceneName);
        stage.show();
    }
}

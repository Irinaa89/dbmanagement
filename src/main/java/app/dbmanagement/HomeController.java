package app.dbmanagement;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HomeController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button add;

    @FXML
    private Button back;

    @FXML
    private TableColumn<Quote, String> dateColumn;

    @FXML
    private Button delete;

    @FXML
    private Button edit;

    @FXML
    private TableColumn<Quote, String> idColumn;

    @FXML
    private TableColumn<Quote, String> last_nameColumn;

    @FXML
    private TableColumn<Quote, String> lessonColumn;

    @FXML
    private TableColumn<Quote, String> quoteColumn;

    @FXML
    private TableColumn<Quote, String> second_nameColumn;

    @FXML
    private TableColumn<Quote, String> first_nameColumn;

    @FXML
    private TableView<Quote> table;

    @FXML
    private Button update;

    @FXML
    private TableColumn<Quote, String> user_idColumn;

    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Quote quote = null;
    DatabaseHandler db = new DatabaseHandler();

    public static int currentQuoteId;
    public static int currentQuoteUserId;

    ObservableList<Quote> quotesList = FXCollections.observableArrayList();

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {

        if (AuthorizationController.user.getRank().equals("guest")){
            delete.setDisable(true);
            add.setDisable(true);
            edit.setDisable(true);
        }

// -- Если пользователь выделил не свою запись, кнопка "удалить" будет недоступна
        if (AuthorizationController.user.getRank().equals("user")) {
            delete.setDisable(true);
            edit.setDisable(true);
            table.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if ((AuthorizationController.user.getId() == table.getSelectionModel().getSelectedItem().user_id)) {
                        delete.setDisable(false);
                        edit.setDisable(false);
                    } else {
                        delete.setDisable(true);
                        edit.setDisable(true);
                    }
                }
            });
        }

        loadDate();

        // -- Обработка событий при нажатии на кнопку "Обновить"
        update.setOnAction(actionEvent -> {
            try {
                refreshTable();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        // -- Обработка событий при нажатии на кнопку "Выйти"
        back.setOnAction(actionEvent -> {
            back.getScene().getWindow().hide();

            Stage stage = new Stage();

            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("authorization.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.setScene(new Scene(root));
            stage.setTitle("authorization");
            stage.show();
        });
    }

    // -- Обновить данные в таблице
    private void refreshTable() throws SQLException {
        quotesList.clear();

        if (AuthorizationController.user.getRank().equals("guest")) query = "SELECT * FROM " + Const.TEACHER_QUOTES_TABLE;
        if (AuthorizationController.user.getRank().equals("admin")) query = "SELECT * FROM " + Const.TEACHER_QUOTES_TABLE;
        if (AuthorizationController.user.getRank().equals("user")) query =
                "SELECT teacher_quotes.id, teacher_quotes.user_id, teacher_quotes.quote, teacher_quotes.last_name, teacher_quotes.first_name, " +
                        "teacher_quotes.second_name, teacher_quotes.lesson, teacher_quotes.date " +
                        "FROM users " +
                        "JOIN teacher_quotes ON (users.id = teacher_quotes.user_id) " +
                        "WHERE (users.study_group ='" + AuthorizationController.user.getStudyGroup() + "')";

        if (AuthorizationController.user.getRank().equals("elder")) query =
                "SELECT teacher_quotes.id, teacher_quotes.user_id, teacher_quotes.quote, teacher_quotes.last_name, teacher_quotes.first_name, " +
                        "teacher_quotes.second_name, teacher_quotes.lesson, teacher_quotes.date " +
                        "FROM users " +
                        "JOIN teacher_quotes ON (users.id = teacher_quotes.user_id) " +
                        "WHERE (users.study_group ='" + AuthorizationController.user.getStudyGroup() + "')";

        preparedStatement = connection.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            quotesList.add(new Quote(
                            resultSet.getInt(Const.TEACHERS_ID),
                            resultSet.getInt(Const.TEACHERS_USERID),
                            resultSet.getString(Const.TEACHERS_QUOTE),
                            resultSet.getString(Const.TEACHERS_LAST_NAME),
                            resultSet.getString(Const.TEACHERS_FIRST_NAME),
                            resultSet.getString(Const.TEACHERS_SECOND_NAME),
                            resultSet.getString(Const.TEACHERS_LESSON),
                            resultSet.getDate(Const.TEACHERS_DATE)
                    )
            );

            table.setItems(quotesList);
        }

    }

    // -- Загрузить данные с базы данных в приложение
    private void loadDate() throws SQLException, ClassNotFoundException {

        connection = db.getDbConnection();
        refreshTable();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        user_idColumn.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        quoteColumn.setCellValueFactory(new PropertyValueFactory<>("quote"));
        last_nameColumn.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        first_nameColumn.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        second_nameColumn.setCellValueFactory(new PropertyValueFactory<>("second_name"));
        lessonColumn.setCellValueFactory(new PropertyValueFactory<>("lesson"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    @FXML // -- Открыть окно с добавлением цитаты на кнопку "Добавить"
    public void getAddView(javafx.scene.input.MouseEvent mouseEvent) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("add.fxml"));

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Добавить цитату");
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
        System.out.println(AuthorizationController.user.getId());
    }

    @FXML // -- Удалить выделенный элемент
    public void deleteItem(MouseEvent event) throws SQLException, ClassNotFoundException {
        connection = db.getDbConnection();

        int id = table.getSelectionModel().getSelectedItem().id;
        query = "DELETE FROM " + Const.TEACHER_QUOTES_TABLE + " WHERE " + Const.TEACHERS_ID + "=" + id;
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.execute();
        refreshTable();
    }

    @FXML // -- Изменить выделенный элемент
    void editItem(MouseEvent event) throws SQLException, ClassNotFoundException, IOException {
        currentQuoteId = table.getSelectionModel().getSelectedItem().id;
        currentQuoteUserId = table.getSelectionModel().getSelectedItem().user_id;
        Parent parent = FXMLLoader.load(getClass().getResource("edit.fxml"));

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Изменить цитату");
        stage.initStyle(StageStyle.UTILITY);
        stage.show();

    }
}

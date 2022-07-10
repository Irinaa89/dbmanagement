package app.dbmanagement;

import java.net.URL;
import java.sql.*;
import java.time.ZoneId;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class AddQuote {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button add;

    @FXML
    private DatePicker addDate;

    @FXML
    private TextField addFirstName;

    @FXML
    private TextField addLastName;

    @FXML
    private TextField addLesson;

    @FXML
    private TextField addQuote;

    @FXML
    private TextField addSecondName;

    @FXML
    private Button clean;

    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Quote quote = null;
    DatabaseHandler db = new DatabaseHandler();
    int quoteId;
    int quoteUserId;
    private boolean update;

    // -- Очистить поля
    @FXML
    void clean(MouseEvent event) {
        addQuote.setText(null);
        addLastName.setText(null);
        addFirstName.setText(null);
        addSecondName.setText(null);
        addLesson.setText(null);
    }

    // -- Добавить запись в таблицу
    @FXML
    void save(MouseEvent event) throws SQLException, ClassNotFoundException {
        String quote = addQuote.getText();
        String last_name = addLastName.getText();
        String first_name = addFirstName.getText();
        String second_name = addSecondName.getText();
        String lesson = addLesson.getText();

        // -- Дополнительная проверка на ошибки с заполнениями полей
        try {
            Date date = Date.valueOf(addDate.getValue());
            if (quote.isEmpty() || last_name.isEmpty() || first_name.isEmpty() || second_name.isEmpty() || lesson.isEmpty() || date.equals("")) {
                System.out.println("Поля пусты");
            } else {
                getQuery();
                insert();
            System.out.println("Запись добавлена");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // -- Добавить запись в sql database
    private void insert() throws SQLException {
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, AuthorizationController.user.getId());
        preparedStatement.setString(2, addQuote.getText());
        preparedStatement.setString(3, addLastName.getText());
        preparedStatement.setString(4, addFirstName.getText());
        preparedStatement.setString(5, addSecondName.getText());
        preparedStatement.setString(6, addLesson.getText());

        // -- Перевести дату в нужный sql формат
        java.util.Date date =
                java.util.Date.from(addDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        preparedStatement.setDate(7, sqlDate);
        preparedStatement.execute();
    }

    // -- Заполнение sql запроса
    private void getQuery() throws SQLException, ClassNotFoundException {
        connection = db.getDbConnection();
        query = "INSERT INTO " + Const.TEACHER_QUOTES_TABLE + " (" + Const.TEACHERS_USERID + ", " + Const.TEACHERS_QUOTE + ", " + Const.TEACHERS_LAST_NAME + ", " +
                Const.TEACHERS_FIRST_NAME + ", " + Const.TEACHERS_SECOND_NAME + ", " + Const.TEACHERS_LESSON
                + ", " + Const.TEACHERS_DATE + ") VALUES(?,?,?,?,?,?,?)";
    }
}

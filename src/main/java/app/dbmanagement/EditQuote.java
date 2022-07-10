package app.dbmanagement;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;


public class EditQuote {

    @FXML
    private Button editClean;

    @FXML
    private DatePicker editDate;

    @FXML
    private TextField editFirstName;

    @FXML
    private TextField editLastName;

    @FXML
    private TextField editLesson;

    @FXML
    private TextField editQuote;

    @FXML
    private Button edit;

    @FXML
    private TextField editSecondName;

    @FXML
    private Text alertText;

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
        editQuote.setText(null);
        editLastName.setText(null);
        editFirstName.setText(null);
        editSecondName.setText(null);
        editLesson.setText(null);
    }

    // -- Добавить запись в таблицу
    @FXML
    void save(MouseEvent event) throws SQLException, ClassNotFoundException {
        String quote = editQuote.getText();
        String last_name = editLastName.getText();
        String first_name = editFirstName.getText();
        String second_name = editSecondName.getText();
        String lesson = editLesson.getText();

        // -- Дополнительная проверка на ошибки с заполнениями полей
        try {
            Date date = Date.valueOf(editDate.getValue());
            if (quote.isEmpty() || last_name.isEmpty() || first_name.isEmpty() || second_name.isEmpty() || lesson.isEmpty() || date.equals("")) {
                //setAlertText("Все поля должны быть заполнены!", "red");
            } else {
                getQuery();
                insert();
                //setAlertText("Запись успешно изменена!", "#31e100");
            }
        } catch (Exception e) {
            //setAlertText("Дата записана в неправильном формате!", "red");
        }

    }

    // -- Добавить запись в sql database
    private void insert() throws SQLException {
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, HomeController.currentQuoteUserId);
        preparedStatement.setString(2, editQuote.getText());
        preparedStatement.setString(3, editLastName.getText());
        preparedStatement.setString(4, editFirstName.getText());
        preparedStatement.setString(5, editSecondName.getText());
        preparedStatement.setString(6, editLesson.getText());

        // -- Перевести дату в нужный sql формат
        java.util.Date date =
                java.util.Date.from(editDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        preparedStatement.setDate(7, sqlDate);
        preparedStatement.execute();
    }

    // -- Заполнение sql запроса
    private void getQuery() throws SQLException, ClassNotFoundException {
        connection = db.getDbConnection();
        query = "UPDATE " + Const.TEACHER_QUOTES_TABLE + " SET "  + Const.TEACHERS_USERID + "=?, " + Const.TEACHERS_QUOTE + "=?, " + Const.TEACHERS_LAST_NAME + "=?, " +
                Const.TEACHERS_FIRST_NAME + "=?, " + Const.TEACHERS_SECOND_NAME + "=?, " + Const.TEACHERS_LESSON
                + "=?, " + Const.TEACHERS_DATE + "=? WHERE id=" + HomeController.currentQuoteId;
    }
}
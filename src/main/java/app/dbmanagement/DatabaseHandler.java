package app.dbmanagement;
import java.sql.*;

public class DatabaseHandler extends Configs{
    Connection dbConnection;
    public Connection getDbConnection() throws ClassNotFoundException, SQLException{
        String connectionString = "jdbc:mysql://" + host + ":" + port + "/" + name;

        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, user, password);

        return dbConnection;
    }

    public void signUpUser(User user) throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO " + Const.USER_TABLE + " (" + Const.USERS_LOGIN + ", " +
                Const.USERS_PASSWORD + ", " + Const.USERS_STUDY_GROUP + ", " + Const.USERS_RANK + ") " +
                "VALUES(?, ?, ?, ?)";

            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, user.getLogin());
            prSt.setString(2, user.getPassword());
            prSt.setString(3, user.getStudyGroup());
            prSt.setString(4, "user");

            prSt.executeUpdate();

    }

    public ResultSet getUser(User user) throws SQLException, ClassNotFoundException {
        ResultSet resSet = null;

        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " +
                Const.USERS_LOGIN + "=? AND " + Const.USERS_PASSWORD + "=?";

        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        prSt.setString(1, user.getLogin());
        prSt.setString(2, user.getPassword());

        resSet = prSt.executeQuery();

        return resSet;
    }
}

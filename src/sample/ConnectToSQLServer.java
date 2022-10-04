package sample;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.event.ActionEvent;
import java.sql.*;

public class ConnectToSQLServer {
    private static String DB_URL = "jdbc:sqlserver://localhost:1433;" + "databaseName = Dictionary_Data;" + "integratedSecurity = true;";
    private static String USER_NAME = "sa";
    private static String PASSWORD = "219907";
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }

    public static int login(String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int flag = -1;
        try {
            connection = ConnectToSQLServer.getConnection();
            preparedStatement = connection.prepareStatement("SELECT password FROM user_info WHERE username COLLATE Latin1_General_CS_AS =?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {//username khong dung
                flag = 0;
            } else {
                while (resultSet.next()) {
                    String pass = resultSet.getString("password");
                    if (pass.equals(password)) {//dang nhap thanh cong
                       flag = 2;
                    } else {//password khong dung
                        flag = 1;
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return flag;
    }
}
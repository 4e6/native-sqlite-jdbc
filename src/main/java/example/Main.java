package example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite::memory:");
            System.out.println(connection);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}

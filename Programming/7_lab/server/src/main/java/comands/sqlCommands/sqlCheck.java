package comands.sqlCommands;

import IO.Console;
import managers.sql.DataBaseManager;
import managers.sql.Security;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class sqlCheck extends sqlCommand {

    public sqlCheck(String name) {
        super(name);
    }

    @Override
    public boolean execute(String tableName, Object param) {
        Connection connection = DataBaseManager.getConnection();

        if (connection == null) {
            throw new IllegalStateException("Database connection not initialized");
        }

        String[] credentials = (String[]) param;
        if (credentials.length < 2) {
            throw new IllegalArgumentException("Expected login and password");
        }

        String login = credentials[0];
        String password = credentials[1];
        String hashedPassword = Security.hashPassword(password);

        switch (tableName.toLowerCase()) {
            case "users":
                String sql = "SELECT * FROM users WHERE login = ? AND password = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setString(1, login);
                    pstmt.setString(2, hashedPassword);

                    try (ResultSet rs = pstmt.executeQuery()) {
                        return rs.next(); // true, если найдена хотя бы одна строка
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }

            default:
                throw new IllegalArgumentException("Unknown table: " + tableName);
        }
    }
}

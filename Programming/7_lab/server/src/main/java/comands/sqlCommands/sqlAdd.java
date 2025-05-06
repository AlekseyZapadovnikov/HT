package comands.sqlCommands;

import IO.Console;
import managers.sql.DataBaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class sqlAdd extends sqlCommand {

    public sqlAdd(String name) {
        super(name);
    }

    @Override
    public boolean execute(String tableName, Object param) {
        String[] addData = (String[]) param;
        Connection connection = DataBaseManager.getConnection(); // Предполагаем добавление метода getConnection()

        if (connection == null) {
            throw new IllegalStateException("Database connection not initialized");
        }

        String sql;
        switch (tableName.toLowerCase()) { // Приводим к нижнему регистру для унификации
            case "users":
                sql = String.format(
                        "INSERT INTO %s (login, password) VALUES (?, ?)",
                        tableName
                );
                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setString(1, addData[0]);
                    pstmt.setString(2, addData[1]);
                    int rowsAffected = pstmt.executeUpdate();
                    return rowsAffected > 0;

                } catch (SQLException e) {
                    Console.getConsole().println("smth went wrong, while adding date to table" + tableName);
                    return false;
                }
            default:
                throw new IllegalArgumentException("Unknown table: " + tableName);
        }
    }
}
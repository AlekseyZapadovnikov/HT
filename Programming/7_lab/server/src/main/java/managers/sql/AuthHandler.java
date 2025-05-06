package managers.sql;

import IO.Request;
import IO.Response;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс для проверки аутентификации пользователей.
 */
public class AuthHandler {

    /**
     * Проверяет логин и пароль пользователя.
     * @param request запрос, содержащий логин, пароль и флаг регистрации
     * @return объект Response с результатом проверки
     */
    public static Response handleAuth(Request request) {
        String[] args = request.getArgs();

        if (args == null || args.length < 3) {
            return new Response("Неверный формат запроса: необходимо передать логин, пароль и флаг регистрации.");
        }

        String login = args[0];
        String rawPassword = args[1];
        String flag = args[2].trim().toLowerCase(); // "да" или "нет"
        String hashedPassword = Security.hashPassword(rawPassword);

        try (Connection connection = DataBaseManager.getConnection()) {
            if (connection == null) {
                return new Response("Ошибка подключения к базе данных.");
            }

            if (flag.equals("да")) {
                // Проверка авторизации
                String checkSql = "SELECT * FROM users WHERE login = ? AND password = ?";
                try (PreparedStatement stmt = connection.prepareStatement(checkSql)) {
                    stmt.setString(1, login);
                    stmt.setString(2, hashedPassword);
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        return new Response("Авторизация прошла успешно.", true);
                    } else {
                        return new Response("Неверный логин или пароль.", false);
                    }
                }
            } else if (flag.equals("нет")) {
                // Регистрация
                String existsSql = "SELECT * FROM users WHERE login = ?";
                try (PreparedStatement existsStmt = connection.prepareStatement(existsSql)) {
                    existsStmt.setString(1, login);
                    ResultSet rs = existsStmt.executeQuery();
                    if (rs.next()) {
                        return new Response("Пользователь с таким логином уже существует.");
                    }
                }

                // Добавление нового пользователя
                String insertSql = "INSERT INTO users (login, password) VALUES (?, ?)";
                try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
                    insertStmt.setString(1, login);
                    insertStmt.setString(2, hashedPassword);
                    insertStmt.executeUpdate();
                    return new Response("Регистрация прошла успешно.", true);
                }
            } else {
                return new Response("Флаг регистрации должен быть 'да' или 'нет'.", false);
            }
        } catch (SQLException e) {
            return new Response("Ошибка при работе с базой данных: " + e.getMessage(), false);
        }
    }
}

package comands.sqlCommands;

import managers.sql.DataBaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Команда для удаления Route (и связанных Coordinates и Location) по ID.
 */
public class sqlRemoveRoute extends sqlCommand {

    public sqlRemoveRoute(String name) {
        super(name);
    }

    /**
     * Удаляет маршрут из БД вместе с зависимыми записями.
     *
     * @param tableName должно быть "route"
     * @param param     Long id маршрута или String[] args, где args[0] = id
     * @return true, если удаление прошло успешно
     */

    public boolean execute(String tableName, Object param, String inputLogin) {
        if (!"route".equalsIgnoreCase(tableName)) {
            throw new IllegalArgumentException("sqlRemoveRoute поддерживает только таблицу 'route'");
        }

        long routeId;
        if (param instanceof Long) {
            routeId = (Long) param;
        } else if (param instanceof String[]) {
            try {
                routeId = Long.parseLong(((String[]) param)[0]);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Неверный ID маршрута", e);
            }
        } else {
            throw new IllegalArgumentException("Параметр должен быть Long или String[] содержащим ID");
        }

        Connection conn = DataBaseManager.getConnection();
        if (conn == null) {
            throw new IllegalStateException("Database connection not initialized");
        }

        String checkOwnerSql = "SELECT user_login, coordinates_id, location_from_id, location_to_id FROM route WHERE id = ?";
        String deleteRouteSql = "DELETE FROM route WHERE id = ?";
        String deleteCoordsSql = "DELETE FROM coordinates WHERE id = ?";
        String deleteLocSql = "DELETE FROM location WHERE id = ?";

        try {
            conn.setAutoCommit(false);

            Long coordsId = null, fromId = null, toId = null;

            // 1) Проверяем владельца и получаем связанные ID
            try (PreparedStatement ps = conn.prepareStatement(checkOwnerSql)) {
                ps.setLong(1, routeId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        conn.rollback();
                        return false; // маршрут не найден
                    }

                    String dbLogin = rs.getString("user_login");
                    if (!inputLogin.equals(dbLogin)) {
                        conn.rollback();
                        System.out.println("Вы не можете удалять чужие объекты");
                        return false;
                    }

                    coordsId = rs.getLong("coordinates_id");
                    fromId = rs.getLong("location_from_id");
                    long tmp = rs.getLong("location_to_id");
                    toId = rs.wasNull() ? null : tmp;
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(deleteRouteSql)) {
                ps.setLong(1, routeId);
                ps.executeUpdate();
            }

            if (coordsId != null) {
                try (PreparedStatement ps = conn.prepareStatement(deleteCoordsSql)) {
                    ps.setLong(1, coordsId);
                    ps.executeUpdate();
                }
            }

            if (fromId != null) {
                try (PreparedStatement ps = conn.prepareStatement(deleteLocSql)) {
                    ps.setLong(1, fromId);
                    ps.executeUpdate();
                }
            }

            if (toId != null) {
                try (PreparedStatement ps = conn.prepareStatement(deleteLocSql)) {
                    ps.setLong(1, toId);
                    ps.executeUpdate();
                }
            }

            conn.commit();
            return true;

        } catch (SQLException ex) {
            try {
                conn.rollback();
            } catch (SQLException ignored) {
            }
            ex.printStackTrace();
            return false;
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ignored) {
            }
        }
    }
}
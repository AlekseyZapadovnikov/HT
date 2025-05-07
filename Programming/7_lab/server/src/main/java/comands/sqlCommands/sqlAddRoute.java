package comands.sqlCommands;

import itemsInArrea.Coordinates;
import itemsInArrea.Location;
import itemsInArrea.Route;
import managers.sql.DataBaseManager;

import java.sql.*;

/**
 * Команда для добавления Route (с вложенными Coordinates и Location) в БД.
 * После успешной вставки устанавливает сгенерированный ID в объект Route.
 */
public class sqlAddRoute extends sqlCommand {

    public sqlAddRoute(String name) {
        super(name);
    }

    public boolean execute(Object param, String userLogin) {
        String tableName = "route";
        if (!"route".equalsIgnoreCase(tableName)) {
            throw new IllegalArgumentException("sqlAddRoute поддерживает только таблицу 'route'");
        }
        if (!(param instanceof Route)) {
            throw new IllegalArgumentException("Ожидается объект Route");
        }
        Route route = (Route) param;
        Connection conn = DataBaseManager.getConnection();
        if (conn == null) {
            throw new IllegalStateException("DB connection not initialized");
        }

        String insertCoordsSql = "INSERT INTO coordinates(x,y) VALUES(?,?)";
        String insertLocSql = "INSERT INTO location(x,y,name) VALUES(?,?,?)";
        String insertRouteSql =
                "INSERT INTO route(name, coordinates_id, creation_date, location_from_id, location_to_id, distance, user_login) " +
                        "VALUES(?,?,?,?,?,?,?) RETURNING id";

        try {
            conn.setAutoCommit(false);

            // 1) coordinates
            long coordsId;
            try (PreparedStatement ps = conn.prepareStatement(insertCoordsSql, Statement.RETURN_GENERATED_KEYS)) {
                Coordinates c = route.getCoordinates();
                ps.setFloat(1, c.getX());
                ps.setFloat(2, c.getY());
                ps.executeUpdate();
                try (ResultSet rk = ps.getGeneratedKeys()) {
                    if (!rk.next()) throw new SQLException("Не удалось получить ID coordinates");
                    coordsId = rk.getLong(1);
                }
            }

            long fromId;
            try (PreparedStatement ps = conn.prepareStatement(insertLocSql, Statement.RETURN_GENERATED_KEYS)) {
                Location lf = route.getFrom();
                ps.setFloat(1, lf.getX());
                ps.setFloat(2, lf.getY());
                ps.setString(3, lf.getName());
                ps.executeUpdate();
                try (ResultSet rk = ps.getGeneratedKeys()) {
                    if (!rk.next()) throw new SQLException("Не удалось получить ID location_from");
                    fromId = rk.getLong(1);
                }
            }

            Long toId = null;
            if (route.getTo() != null) {
                try (PreparedStatement ps = conn.prepareStatement(insertLocSql, Statement.RETURN_GENERATED_KEYS)) {
                    Location lt = route.getTo();
                    ps.setFloat(1, lt.getX());
                    ps.setFloat(2, lt.getY());
                    ps.setString(3, lt.getName());
                    ps.executeUpdate();
                    try (ResultSet rk = ps.getGeneratedKeys()) {
                        if (!rk.next()) throw new SQLException("Не удалось получить ID location_to");
                        toId = rk.getLong(1);
                    }
                }
            }

            // 4) route
            try (PreparedStatement ps = conn.prepareStatement(insertRouteSql)) {
                ps.setString(1, route.getName());
                ps.setLong(2, coordsId);
                ps.setDate(3, Date.valueOf(route.getCreationDate()));
                ps.setLong(4, fromId);
                if (toId != null) ps.setLong(5, toId);
                else ps.setNull(5, Types.BIGINT);
                ps.setLong(6, route.getDistance());
                ps.setString(7, userLogin);  // Передаем userLogin
                try (ResultSet rk = ps.executeQuery()) {
                    if (!rk.next()) throw new SQLException("Не удалось получить ID route");
                    long routeId = rk.getLong(1);
                    route.setId(routeId);
                }
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ignored) {
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ignored) {
            }
        }
    }
}

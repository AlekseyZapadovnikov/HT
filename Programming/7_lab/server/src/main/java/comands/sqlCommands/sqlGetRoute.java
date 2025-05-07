package comands.sqlCommands;

import itemsInArrea.Coordinates;
import itemsInArrea.Location;
import itemsInArrea.Route;
import managers.sql.DataBaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Команда для получения одного Route по его ID из базы.
 */
public class sqlGetRoute extends sqlCommand {

    public sqlGetRoute(String name) {
        super(name);
    }

    /**
     * Не используется. Для совместимости оставляем заглушку.
     */
    @Override
    public boolean execute(String tableName, Object param) {
        throw new UnsupportedOperationException("Use fetchRoute(...) instead");
    }

    /**
     * Считает из БД маршрут с заданным id и возвращает объект Route.
     *
     * @param id идентификатор route в таблице
     * @return объект Route или null, если не найден
     * @throws SQLException в случае ошибок работы с БД
     */
    public Route fetchRoute(long id) throws SQLException {
        Connection conn = DataBaseManager.getConnection();
        if (conn == null || conn.isClosed()) {
            throw new IllegalStateException("Database connection not initialized");
        }

        String sql =
                "SELECT " +
                        "  r.id, r.name, r.creation_date, r.distance, " +
                        "  c.x AS coord_x, c.y AS coord_y, " +
                        "  lf.x AS from_x, lf.y AS from_y, lf.name AS from_name, " +
                        "  lt.x AS to_x,   lt.y AS to_y,   lt.name AS to_name " +
                        "FROM route r " +
                        "JOIN coordinates c ON r.coordinates_id   = c.id " +
                        "JOIN location    lf ON r.location_from_id = lf.id " +
                        "LEFT JOIN location lt ON r.location_to_id   = lt.id " +
                        "WHERE r.id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                // Coordinates
                Coordinates coords = new Coordinates(
                        rs.getFloat("coord_x"),
                        rs.getFloat("coord_y")
                );

                // Location from
                Location from = new Location(
                        rs.getFloat("from_x"),
                        rs.getLong("from_y"),
                        rs.getString("from_name")
                );

                // Location to (может быть null)
                Location to = null;
                String toName = rs.getString("to_name");
                if (toName != null) {
                    to = new Location(
                            rs.getFloat("to_x"),
                            rs.getLong("to_y"),
                            toName
                    );
                }

                // Route
                Route route = new Route();
                route.setId     ( rs.getLong("id") );
                route.setName   ( rs.getString("name") );
                route.setCoordinates(coords);
                route.setId     ( rs.getLong("id") );
                // creationDate в классе Route проставляется автоматически в конструкторе,
                // но если нужно, можно установить через reflection или доработать класс.
                route.setDistance(rs.getLong("distance"));
                // Заполняем from/to
                route.setFrom(from);
                route.setTo(to);

                return route;
            }
        }
    }
}

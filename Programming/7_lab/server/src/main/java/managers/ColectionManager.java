/**
 * Менеджер коллекции Route с синхронизацией с БД.
 */
package managers;

import comands.sqlCommands.sqlAddRoute;
import comands.sqlCommands.sqlRemoveRoute;
import itemsInArrea.Coordinates;
import itemsInArrea.Location;
import itemsInArrea.Route;
import managers.sql.DataBaseManager;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

/**
 * Менеджер коллекции Route с синхронизацией с БД.
 */
public class ColectionManager {
    private final LinkedList<Route> routes = new LinkedList<>();
    private final HashMap<Long, Route> routesMap = new HashMap<>();
    private final sqlAddRoute addRouteCmd = new sqlAddRoute("add_route");
    private final sqlRemoveRoute removeRouteCmd = new sqlRemoveRoute("remove_route");

    private LocalDateTime lastInitTime;
    private LocalDateTime lastUpdateTime;
    private LocalDateTime lastSaveTime;

    public ColectionManager() {
        this.lastInitTime = LocalDateTime.now();
    }

    /**
     * Инициализирует коллекцию из БД: считывает все маршруты и наполняет память.
     *
     * @return true, если загрузка прошла успешно
     */
    public boolean init() {
        Connection conn = DataBaseManager.getConnection();
        if (conn == null) {
            System.err.println("[ERROR] DB connection not initialized");
            return false;
        }
        String sql =
                "SELECT r.id, r.name, r.creation_date, r.distance, " +
                        "c.x AS coord_x, c.y AS coord_y, " +
                        "lf.x AS from_x, lf.y AS from_y, lf.name AS from_name, " +
                        "lt.x AS to_x, lt.y AS to_y, lt.name AS to_name " +
                        "FROM route r " +
                        "JOIN coordinates c ON r.coordinates_id = c.id " +
                        "JOIN location lf ON r.location_from_id = lf.id " +
                        "LEFT JOIN location lt ON r.location_to_id = lt.id";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            routes.clear();
            routesMap.clear();
            while (rs.next()) {
                Route route = new Route();
                long id = rs.getLong("id");

                route.setId(id);
                route.setName(rs.getString("name"));
                route.setCreationDate(rs.getDate("creation_date").toLocalDate());
                Coordinates coords = new Coordinates(
                        rs.getFloat("coord_x"), rs.getFloat("coord_y")
                );
                route.setCoordinates(coords);
                Location from = new Location(
                        rs.getFloat("from_x"), rs.getLong("from_y"), rs.getString("from_name")
                );
                route.setFrom(from);
                String toName = rs.getString("to_name");
                if (toName != null) {
                    Location to = new Location(
                            rs.getFloat("to_x"), rs.getLong("to_y"), toName
                    );
                    route.setTo(to);
                }
                route.setDistance(rs.getLong("distance"));
                routes.add(route);
                routesMap.put(route.getId(), route);
            }
            sortAndUpdateTime();
            lastInitTime = LocalDateTime.now();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean add(Route route) {
        routesMap.put(route.getId(), route);
        routes.add(route);
        sortAndUpdateTime();
        return true;
    }

    public boolean remove(long id) {
        Route r = routesMap.remove(id);
        routes.remove(r);
        if (r == null) return false;
        sortAndUpdateTime();
        return true;
    }

    public Set<Long> getIdSet() {
        return routesMap.keySet();
    }

    public LinkedList<Route> getRoutes() {
        return routes;
    }

    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }

    public LocalDateTime getLastUpdateTime() {
        return lastUpdateTime;
    }

    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }

    public boolean saveRoutes() {
        Connection conn = DataBaseManager.getConnection();
        if (conn == null) {
            System.err.println("[ERROR] DB connection not initialized");
            return false;
        }
        try {
            conn.setAutoCommit(false);
            try (Statement st = conn.createStatement()) {
                st.executeUpdate(
                        "TRUNCATE TABLE route, coordinates, location RESTART IDENTITY CASCADE"
                );
            }
            for (Route r : routes) {
                boolean ok = addRouteCmd.execute("route", r);
                if (!ok) {
                    conn.rollback();
                    System.err.println("[ERROR] Ошибка при сохранении маршрута ID=" + r.getId());
                    return false;
                }
            }
            conn.commit();
            lastSaveTime = LocalDateTime.now();
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

    private void sortAndUpdateTime() {
        Collections.sort(routes);
        lastUpdateTime = LocalDateTime.now();
    }

    public void clear() {
        routes.clear();
        routesMap.clear();
    }

    public boolean isContainId(long id) {
        for (Route route : routes) {
            if (route.getId() == id) return true;
        }
        return false;
    }

    public void update(Route route, long id) {
        remove(id);
        route.setId(id);
        routes.add(route);
        System.out.println("добавили");
    }

    //    private LocalDateTime lastInitTime;
//    private LocalDateTime lastUpdateTime;
//    private LocalDateTime lastSaveTime;
    @Override
    public String toString() {
        String s = "lastInitTime: " + lastInitTime.toString() + "\n";
        s += "lastUpdateTime: " + lastUpdateTime.toString() + "\n";
        if (lastSaveTime != null) {
            s += "lastSaveTime: " + lastSaveTime.toString() + "\n";
        }
        String hs = Integer.valueOf(routes.size()).toString();
        s += "size: " + hs;

        return s;
    }
}

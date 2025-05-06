package managers.sql;

import IO.Console;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseManager {
    private static DataBaseManager dataBaseManager;
    private final Config config;
    private static Connection connection;
    private SSHConnection tunnel;
    Console console;

    private DataBaseManager(Config config) {
        this.config = config;
        this.console = Console.getConsole();
    }

    public static DataBaseManager init(Config config) {
        if (dataBaseManager == null) {
            return new DataBaseManager(config);
        }
        return dataBaseManager;
    }

    public void connect() throws SQLException {
        tunnel = SSHConnection.init(config);

        Thread tunnelThread = new Thread(tunnel);
        tunnelThread.setDaemon(true);
        tunnelThread.start();

        try {
            if (!tunnel.waitUntilConnected(10000)) {
                throw new SQLException("Can't open SSH-tunnel");
            }

            connection = DriverManager.getConnection(
                    String.format("jdbc:postgresql://%s:%s/%s",
                            config.getLocalHost(),
                            config.getLocalPort(),
                            config.getDbName()),
                    config.getSshUser(),
                    config.getDbPassword());
            console.println("Successfully connected to DB");

        } catch (SQLException | InterruptedException e) {
            throw new SQLException("Connection error: " + e.getMessage());
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
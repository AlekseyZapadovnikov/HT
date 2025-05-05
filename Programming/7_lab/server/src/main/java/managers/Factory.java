package managers;

import managers.sql.Config;
import managers.sql.DataBaseManager;
import managers.sql.SSHConnection;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Factory {
    private Factory factory;
    private Scanner scanner;

    public Factory(File file, Scanner scanner) {
    }

    public Factory init(File file) throws FileNotFoundException {
        if (factory == null) {
            scanner = new Scanner(file);
            factory = new Factory(file, scanner);
        }
        return factory;
    }

    public Config createConfig() throws FileNotFoundException {
        if (Config.config == null) {
            return Config.init(scanner);
        }
        return Config.config;
    }

    public DataBaseManager createDataBaseManager() throws FileNotFoundException {
        Config config = createConfig();
        return DataBaseManager.init(config);
    }

    public SSHConnection createSSHConnection() throws FileNotFoundException {
        Config config = createConfig();
        return SSHConnection.init(config);
    }
}

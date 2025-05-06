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
    private File file;

    public Factory(File file) {
        this.file = file;
    }


    public Config createConfig() throws FileNotFoundException {
        if (Config.config == null) {
            scanner = new Scanner(file);
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

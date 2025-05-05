package managers.sql;


import IO.Console;
import com.jcraft.jsch.Session;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Config {
    private final String sshUser;
    private final String sshHost;
    private final int sshPort;
    private final int localPort;
    private final String localHost;
    private final String remoteHost;
    private final int remotePort;
    private final String sshPassword;
    private final String dbName;
    private final String dbPassword;

    public static Config config;


    private Config(Scanner scanner) {
        sshUser = scanner.nextLine();
        sshHost = scanner.nextLine();
        sshPort = scanner.nextInt();
        localPort = scanner.nextInt();
        localHost = scanner.nextLine();
        remoteHost = scanner.nextLine();
        remotePort = scanner.nextInt();
        sshPassword = scanner.nextLine();
        dbName = scanner.nextLine();
        dbPassword = scanner.nextLine();
        scanner.close();
    }

    public static Config init(Scanner scanner) throws FileNotFoundException{
        if (config == null){
            return new Config(scanner);
        }
        return config;
    }

    public String getSshUser() {
        return sshUser;
    }

    public String getSshPassword() {
        return sshPassword;
    }

    public int getSshPort() {
        return sshPort;
    }

    public int getLocalPort() {
        return localPort;
    }

    public String getSshHost() {
        return sshHost;
    }

    public int getRemotePort() {
        return remotePort;
    }

    public String getRemoteHost() {
        return remoteHost;
    }

    public String getLocalHost() {
        return localHost;
    }

    public String getDbName() {
        return dbName;
    }

    public String getDbPassword() {
        return dbPassword;
    }
}

package managers.sql;

import IO.Console;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SSHConnection implements Runnable{
    private final String sshUser;
    private final String sshHost;
    private final int sshPort;
    private final int localPort;
    private final String remoteHost;
    private final int remotePort;
    private final String sshPassword;

    private boolean isConnected = false;
    private Session session;
    public static SSHConnection tunnel;
    private Console console;

    private SSHConnection(String sshUser, String sshPassword, String sshHost, int sshPort,
                          int localPort, String remoteHost, int remotePort) {
        this.sshUser = sshUser;
        this.sshPassword = sshPassword;
        this.sshHost = sshHost;
        this.sshPort = sshPort;
        this.localPort = localPort;
        this.remoteHost = remoteHost;
        this.remotePort = remotePort;
        this.console = Console.getConsole();
    }


    public static SSHConnection init(Config c) {
        if (tunnel == null) {
            tunnel = new SSHConnection(c.getSshUser(), c.getSshPassword(), c.getSshHost(), c.getSshPort(), c.getLocalPort(), c.getRemoteHost(), c.getRemotePort());
        }
        return tunnel;
    }

    public boolean waitUntilConnected(long timeoutMs) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        while (!isConnected && (System.currentTimeMillis() - startTime) < timeoutMs) {
            Thread.sleep(100);
        }
        return isConnected;
    }

    public void close() {
        if (session != null && session.isConnected()) {
            session.disconnect();
            isConnected = false;
        }
    }

    @Override
    public void run() {
        JSch jsch = new JSch();

        try {
            session = jsch.getSession(sshUser, sshHost, sshPort);
            session.setPassword(sshPassword);
            session.setConfig("StrictHostKeyChecking", "no");

            console.println("Connecting SSH...");
            session.connect();

            session.setPortForwardingL(localPort, remoteHost, remotePort);
            console.println(String.format("Tunnel created: localhost:%d -> %s:%d to %s:%d%n",
                    localPort, remoteHost, remotePort, sshHost, sshPort));

            isConnected = true;

            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(1000);
            }

        } catch (JSchException | InterruptedException e) {
            console.println("SSH-tunnel error: " + e.getMessage());
        } finally {
            close();
        }
    }
}

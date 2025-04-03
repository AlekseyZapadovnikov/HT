package managers;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.channels.*;

public class ServerConnectionManager {
    private final int port;
    private ServerSocketChannel serverChannel;
    private SocketChannel clientChannel;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public ServerConnectionManager(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress(port));
    }

    public void acceptConnection() throws IOException {
        clientChannel = serverChannel.accept();

        // Важно: сначала создаём выходной поток
        oos = new ObjectOutputStream(Channels.newOutputStream(clientChannel));
        ois = new ObjectInputStream(Channels.newInputStream(clientChannel));

    }

    public void sendData(NetworkMessage data) throws IOException {
        oos.writeObject(data);
        oos.flush();
    }

    public void sendData(String message) throws IOException {
        oos.writeObject(message);
        oos.flush();
    }

    public NetworkMessage receiveData() throws IOException, ClassNotFoundException {
        return (NetworkMessage) ois.readObject();
    }

    public void close() throws IOException {
        if (ois != null) ois.close();
        if (oos != null) oos.close();
        if (clientChannel != null) clientChannel.close();
        if (serverChannel != null) serverChannel.close();
    }
}
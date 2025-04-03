package managers;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.channels.*;

public class ClientConnectionManager {
    private final String host;
    private final int port;
    private SocketChannel socketChannel;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public ClientConnectionManager(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect() throws IOException {
        socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(host, port));

        // Важно: порядок создания потоков должен совпадать с сервером
        oos = new ObjectOutputStream(Channels.newOutputStream(socketChannel));
        ois = new ObjectInputStream(Channels.newInputStream(socketChannel));

        System.out.println("Подключено к серверу: " + socketChannel.getRemoteAddress());
    }

    public void sendData(NetworkMessage data) throws IOException {
        oos.writeObject(data);
        oos.flush();
    }

    public NetworkMessage receiveData() throws IOException, ClassNotFoundException {
        return (NetworkMessage) ois.readObject();
    }

    public void close() throws IOException {
        if (ois != null) ois.close();
        if (oos != null) oos.close();
        if (socketChannel != null) socketChannel.close();
    }
}
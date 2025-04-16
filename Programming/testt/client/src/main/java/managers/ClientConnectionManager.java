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
    private volatile boolean isConnected = false; // Добавленное поле

    public ClientConnectionManager(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect() throws IOException {
        try {
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(true);
            socketChannel.connect(new InetSocketAddress(host, port));

            // Порядок создания должен совпадать с серверной частью
            oos = new ObjectOutputStream(Channels.newOutputStream(socketChannel));
            ois = new ObjectInputStream(Channels.newInputStream(socketChannel));

            isConnected = true; // Устанавливаем флаг подключения
            System.out.println("Подключено к серверу: " + socketChannel.getRemoteAddress());
        } catch (IOException e) {
            close();
            throw e;
        }
    }

    public void sendData(NetworkMessage data) throws IOException {
        if (!isConnected) {
            throw new IOException("Соединение не установлено");
        }
        oos.writeObject(data);
        oos.flush();
    }

    public NetworkMessage receiveData() throws IOException, ClassNotFoundException {
        if (!isConnected) {
            throw new IOException("Соединение не установлено");
        }
        return (NetworkMessage) ois.readObject();
    }

    public void close() {
        try {
            if (ois != null) ois.close();
            if (oos != null) oos.close();
            if (socketChannel != null) socketChannel.close();
        } catch (IOException e) {
            System.err.println("Ошибка при закрытии соединения: " + e.getMessage());
        } finally {
            isConnected = false; // Всегда сбрасываем флаг
        }
    }

    // Геттер для проверки состояния подключения
    public boolean isConnected() {
        return isConnected && socketChannel != null && socketChannel.isConnected();
    }
}
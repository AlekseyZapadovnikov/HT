package net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;

public class Network {
    private final ServerSocketChannel serverChannel;
    private final boolean BLOCKING_MODE = false;

    private final Map<Socket, ObjectInputStream> inputStreams = new HashMap<>();
    private final Map<Socket, ObjectOutputStream> outputStreams = new HashMap<>();

    public Network(int PORT) throws IOException {
        serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress(PORT));
        serverChannel.configureBlocking(BLOCKING_MODE);
    }

    public Socket acceptConnection() throws IOException {
        SocketChannel clientChannel = serverChannel.accept();
        if (clientChannel != null) {
            Socket client = clientChannel.socket();
            if (!outputStreams.containsKey(client)) {
                ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
                outputStreams.put(client, oos);
                ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
                inputStreams.put(client, ois);
            }
            return client;
        }
        return null;
    }

    public void write(Socket client, Object obj) throws IOException {
        ObjectOutputStream oos = outputStreams.get(client);
        if (oos != null) {
            oos.writeObject(obj);
            oos.flush();
        }
    }

    public Object read(Socket client) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = inputStreams.get(client);
        if (ois != null) {
            return ois.readObject();
        }
        return null;
    }

    public void removeStreams(Socket client) {
        inputStreams.remove(client);
        outputStreams.remove(client);
    }

    public void closeServer() throws IOException {
        serverChannel.close();
    }
}

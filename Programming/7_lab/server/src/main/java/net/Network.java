package net;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Network {
    private final ServerSocketChannel serverChannel;
    private final boolean BLOCKING_MODE = false;

    public Network(int PORT) throws IOException {
        this.serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress(PORT));
        serverChannel.configureBlocking(BLOCKING_MODE);
    }

    public Socket acceptConnection() throws IOException{
        SocketChannel clientChannel = serverChannel.accept();
        if (clientChannel != null) {
            return clientChannel.socket();
        }
        return null;
    }

    public void closeServer() throws IOException{
        serverChannel.close();
    }

    public void write(Socket client, Object obj) throws IOException{
        ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
        oos.writeObject(obj);
        oos.flush();
    }

    public Object read(Socket client) throws IOException, ClassNotFoundException{
        InputStream is = client.getInputStream();
        if(is.available() <= 0){
            return null;
        }
        ObjectInputStream ois = new ObjectInputStream(is);
        return ois.readObject();
    }
}

package net;

import IO.Request;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.TimeUnit;

public class Network {
    private String HOST;
    private int PORT;
    private Socket socket;
    private final int CONNECTION_ATTEMPTS = 10;
    private final int RECONNECTION_TIMEOUT = 5;

    public Network(String SERVER_ADDRESS, int PORT) throws IOException{
        this.HOST = SERVER_ADDRESS;
        this.PORT = PORT;
        connect();
    }

    public void connect() throws IOException{
        for(int i = 0; i < CONNECTION_ATTEMPTS; i++){
            try {
                socket = new Socket(HOST, PORT);
                return;
            }catch (SocketException e){
                System.out.println("Trying to connect...");
                try {
                    TimeUnit.SECONDS.sleep(RECONNECTION_TIMEOUT);
                }catch (InterruptedException err){}
            }
        }
        throw new SocketException("Failed to connect to the server");
    }

    public void close() throws IOException{
        if(socket != null){
            socket.close();
        }
    }

    public void write(Object obj) throws IOException{
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(obj);
        oos.flush();
    }

    public Object read() throws IOException, ClassNotFoundException{
        if (socket.getInputStream().available() > 0) {
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            return is.readObject();
        }
        return null;
    }
}


package org.example;

import managers.*;
import net.ServerConnection;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Runner runner = new Runner();
        ColectionManager collectionManager = runner.getColectionManager();
        ServerConnection server = new ServerConnection(8080, collectionManager);
        server.start();
    }
}
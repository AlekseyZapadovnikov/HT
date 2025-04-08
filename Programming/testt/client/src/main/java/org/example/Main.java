package org.example;

import IO.Console;
import IO.Request;
import IO.Response;
import managers.ClientConnectionManager;

import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Console console = new Console();
        ClientConnectionManager connectionManager;
        try {
            connectionManager = new ClientConnectionManager("localhost", 1111);
            connectionManager.connect();

            while (true){
                Request request = console.readCommand();
                connectionManager.sendData(request);
                Response respone = (Response) connectionManager.receiveData();
                console.printResponse(respone);
            }
        } catch (IOException e) {
            console.println("Connection error: " + e.getMessage());
        } catch (ClassNotFoundException e){
            console.println("Class not found exeption happened, developer durachek");
        }
    }
}
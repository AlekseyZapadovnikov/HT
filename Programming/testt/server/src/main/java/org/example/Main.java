package org.example;

import IO.Request;
import IO.Response;
import itemsInArrea.Route;
import managers.CommandManager;
import managers.ServerConnectionManager;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerConnectionManager connectionManager = new ServerConnectionManager(1111);
        connectionManager.start();
        connectionManager.acceptConnection();
        CommandManager cm = null;
        try {
            Runner runner = new Runner();
            runner.run();
            cm = runner.getCommandManager();
        } catch (Exception e){
            connectionManager.sendData("An error occurred while starting the server");
        }
        while (true){
            Response response;
            try {
                Request request = (Request) connectionManager.receiveData();
                String commandName = request.getCommand();
                if (request.isContainRoute()) {
                    cm.getCommandByName(commandName).execute(request.getRoute());
                }

            } catch (ClassNotFoundException e) {
                response = new Response(e);
            } catch (Exception e){
                response = new Response(e);
            }
        }
        // Пример обмена данными:
        // NetworkMessage message = connectionManager.receiveData();
        // connectionManager.sendData(new NetworkMessage("Response"));

        //connectionManager.close();
    }
}
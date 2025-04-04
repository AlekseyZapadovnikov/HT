package org.example;

import IO.Request;
import IO.Response;
import itemsInArrea.Route;
import managers.CommandManager;
import managers.ControlManager;
import managers.ServerConnectionManager;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerConnectionManager connectionManager = new ServerConnectionManager(1111);
        connectionManager.start();
        connectionManager.acceptConnection();
        CommandManager cm = null;
        ControlManager control = null;
        try {
            control = new ControlManager();
            control.run();
            cm = control.getCommandManager();
        } catch (Exception e){
            connectionManager.sendData(new Response(e, "An error occurred while starting the server"));
        }
        while (true){
            Response response;
            try {
                Request request = (Request) connectionManager.receiveData();
                String commandName = request.getCommand();
                if (request.isContainRoute()) {
                    response = cm.getCommandByName(commandName).execute(request.getRoute());
                } else {
                    response = cm.getCommandByName(commandName).execute(request.getArgs());
                }
//                response = control.giveResponse(commandName);
                connectionManager.sendData(response);
            } catch (ClassNotFoundException e) {
                response = new Response(e, "Class not found");
            } catch (FileNotFoundException e){
                response = new Response(e, "There is no such file on the server");
            } catch (Exception e){
                response = new Response(e, "I give up");
            }
        }
    }
}
package org.example;

import IO.Request;
import IO.Response;
import comands.Command;
import comands.Help;
import comands.Show;
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
            Help help = new Help("1", "1", cm);
            Response r = help.execute(new String[0]);
            System.out.println(r);
        } catch (Exception e) {
            connectionManager.sendData(new Response(e, "An error occurred while starting the server"));
        }
        while (true) {
            Response response = null;
            try {
                System.out.println("Ожидание запроса...");
                Request request = (Request) connectionManager.receiveData();
                String commandName = request.getCommand();
                Command command = cm.getCommandByName(commandName);

                // Обработка команды
                if (command == null) {
                    response = new Response("Неизвестная команда: " + commandName);
                } else if (request.isContainRoute()) {
                    response = command.execute(request.getRoute());
                } else {
                    response = command.execute(request.getArgs());
                }

                // Отправка ответа только здесь
                connectionManager.sendData(response);
                System.out.println("Ответ отправлен: " + response.getMessage());

            } catch (ClassNotFoundException e) {
                response = new Response(e, "Ошибка десериализации");
            } catch (FileNotFoundException e) {
                response = new Response(e, "Файл не найден");
            } catch (IOException e) {
                System.out.println("Клиент отключился: " + e.getMessage());
                break;
            } catch (Exception e) {
                response = new Response(e, "Внутренняя ошибка сервера");
            }
        }
    }
}
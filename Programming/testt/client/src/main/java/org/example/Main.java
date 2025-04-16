package org.example;

import IO.Console;
import IO.Request;
import IO.Response;
import managers.ClientConnectionManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Console console = new Console();
        ClientConnectionManager connectionManager = null;

        while (true) {
            try {
                // Подключение при необходимости
                if (connectionManager == null || !connectionManager.isConnected()) {
                    connectionManager = new ClientConnectionManager("localhost", 1111);
                    connectionManager.connect();
                    console.println("Подключено к серверу.");
                }

                // Чтение команды
                Request request = console.readCommand();
                String cmd = request.getCommand();

                // Обработка выхода
                if (cmd.equalsIgnoreCase("exit")) {
                    console.println("Завершение работы...");
                    connectionManager.close();
                    break;
                }

                // Обработка execute_script
                if (cmd.equalsIgnoreCase("execute_script")) {
                    String[] scriptArgs = request.getArgs();

                    // Проверка аргументов
                    if (scriptArgs == null || scriptArgs.length == 0 || scriptArgs[0].trim().isEmpty()) {
                        console.println("Ошибка: укажите имя файла. Формат: execute_script file.txt");
                        continue;
                    }

                    processScript(console, connectionManager, scriptArgs[0]);
                    continue;
                }

                // Отправка обычных команд
                sendAndReceive(console, connectionManager, request);

            } catch (IOException e) {
                handleConnectionError(console, connectionManager);
                break;
            } catch (ClassNotFoundException e) {
                console.println("Ошибка формата данных: " + e.getMessage());
            }
        }
    }

    private static void processScript(Console console, ClientConnectionManager cm, String fileName) {
        try {
            File file = new File("client/src/main/resources/" + fileName);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                Request request = console.parseLine(line);
                if (request.getCommand().equalsIgnoreCase("exit")) {
                    console.println("Обнаружен exit в скрипте! Прерывание.");
                    System.exit(0);
                }
                sendAndReceive(console, cm, request);
            }
        } catch (FileNotFoundException e) {
            console.println("Файл не найден: " + fileName);
        } catch (ClassNotFoundException e) {
            console.println("Класс не найден");
        } catch (IOException e){
            console.println("Произошла ошибка при попытке отправить сообщение на сервер");
        }
    }

    private static void sendAndReceive(Console console, ClientConnectionManager cm, Request request)
            throws IOException, ClassNotFoundException {
        cm.sendData(request);
        Response response = (Response) cm.receiveData();
        console.printResponse(response);
    }

    private static void handleConnectionError(Console console, ClientConnectionManager cm) {
        console.println("Ошибка соединения! Попытка переподключения...");
        if (cm != null) {
            cm.close();
        }
    }
}
package net;

import IO.Request;
import IO.Response;
import IO.ServerConsole;
import comands.Command;
import managers.CommandManager;
import managers.Factory;
import managers.Runner;
import managers.sql.AuthHandler;
import managers.sql.DataBaseManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;

import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

public class Server {
    private boolean isRunning = true;
    private final Set<Socket> clients = new HashSet<>();
    private Network network;
    private final Runner runner;
    private final ServerConsole console;
    private final CommandManager commandManager;

    private final Map<Socket, Boolean> authenticatedClients = new HashMap<>();

    public Server(Runner runner) {
        this.runner = runner;
        this.console = runner.getServerConsole();
        this.commandManager = runner.getCommandManager();
    }

    public static void main(String[] args) throws SQLException {
        Runner runner = new Runner();
        Server server = new Server(runner);
        server.run();
    }

    private static class localConfig {
        final static int PORT = 1488;
        final static String EXIT_WORD = "exit";
    }

    private void run() throws SQLException {
        try {
            File properties = new File("C:\\Users\\Asus\\Desktop\\repo\\HT\\Programming\\7_lab\\server\\src\\main\\resources\\properties.txt");
            Factory factory = new Factory(properties);
            DataBaseManager dbManager = factory.createDataBaseManager();
            dbManager.connect();
        } catch (FileNotFoundException e) {
            console.println("всё плохо");
        }
        try {
            network = new Network(localConfig.PORT);
            console.log(Level.INFO, "Server started on port " + localConfig.PORT);
            console.log(Level.INFO, "Type '" + localConfig.EXIT_WORD + "' to stop the server");
            runner.run();
            while (isRunning) {
                try {
//                    checkConsole();
                    Socket client = network.acceptConnection();
                    if (client != null) {
                        clients.add(client);
                        console.log(Level.INFO, "New client connected: " + client.getInetAddress());
                    }
                    processClients();
                } catch (IOException e) {
                }
            }
            runner.closeApp();
            network.closeServer();
            console.log(Level.OFF, "Server stopped");
        } catch (IOException e) {
            console.log(Level.WARNING, "Server error: " + e.getMessage());
        }
    }

    private void processClients() throws IOException {
        System.out.println("тут1");
        for (Socket client : clients) {
            System.out.println("тут2");
            try {
                Request userRequest = (Request) network.read(client);
                if (userRequest == null) continue;

                if (!authenticatedClients.getOrDefault(client, false)) {
                    Response authResponse = AuthHandler.handleAuth(userRequest);
                    network.write(client, authResponse);
                    if (authResponse.getMessage().contains("успешно")) {
                        authenticatedClients.put(client, true);
                    } else {
                        clients.remove(client); // выкидываем клиента
                        client.close();
                        console.log(Level.INFO, "Client authentication failed: " + client.getInetAddress());
                    }
                    continue; // на этом обработка закончена
                }

                Response serverResponse;
                String commandName = userRequest.getCommand();

                if (commandManager.hasCommand(commandName)) {
                    if (userRequest.isContainRoute()) {
                        serverResponse = commandManager.getCommandByName(commandName).execute(userRequest.getRoute());
                    } else {
                        Command command = commandManager.getCommandByName(commandName);
                        serverResponse = command.execute(userRequest.getArgs());
                    }
                } else {
                    serverResponse = new Response("we haven’t such command, enter help to see all valid commands");
                }
                network.write(client, serverResponse);

            } catch (ClassNotFoundException e) {
                console.log(Level.WARNING, "Deserialization error: " + e.getMessage());
            } catch (SocketException e) {
                clients.remove(client);
                authenticatedClients.remove(client);
                console.log(Level.INFO, "Client disconnected: " + client.getInetAddress());
            }
        }
    }
}
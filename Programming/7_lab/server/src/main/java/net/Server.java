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

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;
import java.util.logging.Level;

public class Server {
    private boolean isRunning = true;
    private final Set<Socket> clients = ConcurrentHashMap.newKeySet();
    private final Map<Socket, Boolean> authenticatedClients = new ConcurrentHashMap<>();

    private Network network;
    private final Runner runner;
    private final ServerConsole console;
    private final CommandManager commandManager;

    private final ExecutorService readExecutor = Executors.newCachedThreadPool();
    private final ExecutorService processExecutor = Executors.newCachedThreadPool();
    private final ExecutorService sendExecutor = Executors.newFixedThreadPool(4);

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
                    Socket client = network.acceptConnection();
                    if (client != null) {
                        clients.add(client);
                        console.log(Level.INFO, "New client connected: " + client.getInetAddress());
                        readExecutor.execute(() -> {
                            String threadName = Thread.currentThread().getName();
                            console.log(Level.INFO, "[READ THREAD] Started for " + client.getInetAddress() + " | " + threadName);
                            handleClient(client);
                        });
                    }
                } catch (IOException e) {
                    console.log(Level.WARNING, "Error accepting client: " + e.getMessage());
                }
            }

            shutdownExecutors();
            runner.closeApp();
            network.closeServer();
            console.log(Level.OFF, "Server stopped");

        } catch (IOException e) {
            console.log(Level.WARNING, "Server error: " + e.getMessage());
        }
    }

    private void handleClient(Socket client) {
        while (!client.isClosed()) {
            try {
                Request request = (Request) network.read(client);
                if (request == null) continue;

                if (!authenticatedClients.getOrDefault(client, false)) {
                    Response authResponse = AuthHandler.handleAuth(request);
                    sendExecutor.execute(() -> {
                        String threadName = Thread.currentThread().getName();
                        console.log(Level.INFO, "[SEND AUTH THREAD] To " + client.getInetAddress() + " | " + threadName);
                        sendResponse(client, authResponse);
                    });
                    authenticatedClients.put(client, authResponse.getMessage().contains("успешно"));
                    continue;
                }

                processExecutor.execute(() -> {
                    String processThreadName = Thread.currentThread().getName();
                    console.log(Level.INFO, "[PROCESS THREAD] For " + client.getInetAddress() + " | " + processThreadName);
                    try {
                        Response response = processRequest(request);
                        sendExecutor.execute(() -> {
                            String sendThreadName = Thread.currentThread().getName();
                            console.log(Level.INFO, "[SEND THREAD] For " + client.getInetAddress() + " | " + sendThreadName);
                            sendResponse(client, response);
                        });
                    } catch (Exception e) {
                        console.log(Level.WARNING, "Ошибка при обработке запроса: " + e.getMessage());
                    }
                });

            } catch (EOFException | SocketException e) {
                disconnectClient(client);
                break;
            } catch (IOException | ClassNotFoundException e) {
                console.log(Level.WARNING, "Ошибка клиента: " + client.getInetAddress() + " -> " + e.getMessage());
                disconnectClient(client);
                break;
            }
        }
    }

    private Response processRequest(Request request) {
        String commandName = request.getCommand();
        if (commandManager.hasCommand(commandName)) {
            Command command = commandManager.getCommandByName(commandName);
            if (request.isContainRoute() && request.getUserLogin() != null) {
                System.out.println("3");
                return command.execute(request.getRoute(), request.getUserLogin());
            } else if (request.getUserLogin() != null) {
                System.out.println("here");
                System.out.println(request.getArgs());
                return command.execute(request.getArgs(), request.getUserLogin());
            } else {
                System.out.println("4");
                return command.execute(request.getArgs());
            }
        }
        return new Response("we haven’t such command, enter help to see all valid commands");
    }


    private void sendResponse(Socket client, Response response) {
        try {
            network.write(client, response);
        } catch (IOException e) {
            console.log(Level.WARNING, "Ошибка при отправке ответа клиенту: " + e.getMessage());
        }
    }

    private void disconnectClient(Socket client) {
        try {
            network.removeStreams(client);
            client.close();
        } catch (IOException ignored) {
        }
        clients.remove(client);
        authenticatedClients.remove(client);
        console.log(Level.INFO, "Client disconnected: " + client.getInetAddress());
    }

    private void shutdownExecutors() {
        readExecutor.shutdown();
        processExecutor.shutdown();
        sendExecutor.shutdown();
        try {
            readExecutor.awaitTermination(5, TimeUnit.SECONDS);
            processExecutor.awaitTermination(5, TimeUnit.SECONDS);
            sendExecutor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException ignored) {
        }
    }
}


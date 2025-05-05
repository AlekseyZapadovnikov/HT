package net;

import IO.Request;
import IO.Response;
import IO.ServerConsole;
import comands.Command;
import managers.CommandManager;
import managers.Factory;
import managers.Runner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;

public class Server {
    private boolean isRunning = true;
    private final Set<Socket> clients = new HashSet<>();
    private Network network;
    private final Runner runner;
    private final ServerConsole console;
    private final CommandManager commandManager;

    public Server(Runner runner) {
        this.runner = runner;
        this.console = runner.getServerConsole();
        this.commandManager = runner.getCommandManager();
    }

    public static void main(String[] args) {
        Runner runner = new Runner();
        Server server = new Server(runner);
        server.run();
    }

    private static class Config {
        final static int PORT = 6789;
        final static String EXIT_WORD = "exit";
    }

    private void run() {
        try {
            File properties = new File("Programming/7_lab/server/src/main/resources/properties.txt");
            Scanner serverScanner = new Scanner(properties);
            Factory factory = new Factory(properties, serverScanner);
        } catch (FileNotFoundException e) {
            console.println("всё плохо");
        }
        try {
            network = new Network(Config.PORT);
            console.log(Level.INFO, "Server started on port " + Config.PORT);
            console.log(Level.INFO, "Type '" + Config.EXIT_WORD + "' to stop the server");
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
        for (Socket client : clients) {
            try {
                try {
                    Request userRequest = (Request) network.read(client);
                    if (userRequest == null) {
                        continue;
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
                        serverResponse = new Response("we havn`t such command, enter help to see all valid commands");
                    }
                    network.write(client, serverResponse);
                } catch (ClassNotFoundException e) {
                    console.log(Level.WARNING, "Deserialization error: " + e.getMessage());
                }
            } catch (SocketException e) {
                clients.remove(client);
                console.log(Level.INFO, "Client disconnected: " + client.getInetAddress());
            }
        }
    }
}

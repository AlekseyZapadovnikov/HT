package net;

import IO.Request;
import IO.Response;
import IO.ServerConsole;
import comands.Command;
import managers.CommandManager;
import managers.Runner;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashSet;
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
//        if(args.length != 1){
//            System.out.println("Required name file name");
//            return;
//        }
//        String fileName = args[0];
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

//    private void checkConsole() throws IOException {
//        if (System.in.available() > 0) {
//            String line = console.bufferedRead();
//            if(line == null){
//                return;
//            }
//            line = line.trim();
//            if (Config.EXIT_WORD.equalsIgnoreCase(line.toUpperCase())) {
//                isRunning = false;
//            }else {
//                runner.processServerCommand(line);
//            }
//        }
//    }

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

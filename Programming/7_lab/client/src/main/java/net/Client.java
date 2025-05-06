package net;

import IO.Console;
import IO.FileReader;
import IO.Request;
import IO.Response;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;


public class Client {
    private final Console console = new Console();
    private Network network;
    Request userRequest;
    private ClientsBaseManager baseManager;

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.run();
    }

    private static class Config {
        static final String SERVER_ADDRESS = "localhost";
        static final int SERVER_PORT = 1488;
        static final String EXIT_WORD = "exit";
        static final String SCRIPT_WORD = "execute_script";
    }

    private void doRequest(String line) throws IOException {
        try {
            userRequest = Console.parseLine(line);
            network.write(userRequest);
            Response serverResponse = (Response) network.read();
            while (serverResponse == null) {
                serverResponse = (Response) network.read();
            }
//            String serverMessage = serverResponse.getMessage();
            if (!serverResponse.isEmpty()) {
                console.printResponse(serverResponse);
            } else {
                System.out.println("Server`s empty answer");
            }
        } catch (ClassNotFoundException e) {
            console.println("Deserialization error: " + e.getMessage());
        }
    }

    private void scriptMode(String[] args) throws IOException {
        if (args.length != 1) {
            console.println("Required script name");
            return;
        }
        String fileName = args[0];
        FileReader reader;
        try {
            reader = new FileReader(fileName);
        } catch (FileNotFoundException e) {
            console.println("File doesn't exist");
            return;
        }

        boolean isScriptRunning = true;
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            if (line == null) {
                break;
            }
            if ((Config.SCRIPT_WORD + " " + fileName).equalsIgnoreCase(line)) {
                console.println("Recursion is not allowed for execution");
                continue;
            }
            isScriptRunning = consoleMode(line, isScriptRunning);
        }
    }

    private boolean consoleMode(String line, boolean isRunning) throws IOException {
        String command = console.parseCommand(line);
        if (Config.EXIT_WORD.equalsIgnoreCase(command)) {
            return false;
        }
        if (Config.SCRIPT_WORD.equals(command)) {
            scriptMode(userRequest.getArgs());
        }

        doRequest(line);
        return isRunning;
    }

    private void run() throws IOException {
        try {
            boolean isRunning = true;
            network = new Network(Config.SERVER_ADDRESS, Config.SERVER_PORT);
            console.println("Connected to server");

            baseManager = new ClientsBaseManager(network, console);
            short counter = 0;
            for (int i = 0; i < 5; i++){
                boolean flag = baseManager.parseClientStatus(console.inputCheckIn());
                if (flag){
                    break;
                } else if (counter++ > 3){
                    console.println("запрос на подключение отклонён");
                    System.exit(0);
                }
            }

            console.println("Enter command (or 'exit' to quit):");
            while (isRunning) {
                String line = console.nextLine();
                if (line == null) {
                    continue;
                }
                userRequest = Console.parseLine(line);
                isRunning = consoleMode(userRequest.getCommand(), isRunning);
            }
            network.close();
        } catch (SocketException e) {
            console.println("Server is temporarily unavailable");
            run();
        } catch (IOException e) {
            console.println("\nClient stopped");
        } catch (ClassNotFoundException e) {
            console.println("serialization error has occurred, it might happened if you entered uncorrected check-in data");
            network.close();
            run();
        }
    }
}
package net;


import IO.Console;
import IO.Request;
import IO.Response;
import IO.FileReader;

import java.io.*;
import java.net.SocketException;


public class Client {
    private final Console console = new Console();
    private Network network;
    Request userRequest;

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }

    private static class Config{
        static final String SERVER_ADDRESS = "localhost";
        static final int SERVER_PORT = 6789;
        static final String EXIT_WORD = "exit";
        static final String SCRIPT_WORD = "execute_script";
    }

    private void doRequest(String line) throws IOException{
        try{
            userRequest = Console.parseLine(line);
            network.write(userRequest);
            Response serverResponse = (Response) network.read();
            while (serverResponse == null){
                serverResponse = (Response) network.read();
            }
//            String serverMessage = serverResponse.getMessage();
            if(!serverResponse.isEmpty()){
                console.printResponse(serverResponse);
            } else {
                System.out.println("Server`s empty answer");
            }
        } catch (ClassNotFoundException e){
            console.println("Deserialization error: " + e.getMessage());
        }
    }

    private void scriptMode(String[] args) throws IOException{
        if(args.length != 1){
            console.println("Required script name");
            return;
        }
        String fileName = args[0];
        FileReader reader;
        try{
            reader = new FileReader(fileName);
        }catch (FileNotFoundException e){
            console.println("File doesn't exist");
            return;
        }

        boolean isScriptRunning = true;
        while (reader.hasNextLine()){
            String line = reader.nextLine();
            if (line == null){
                break;
            }
            if((Config.SCRIPT_WORD + " " + fileName).equalsIgnoreCase(line)){
                console.println("Recursion is not allowed for execution");
                continue;
            }
            isScriptRunning = consoleMode(line, isScriptRunning);
        }
    }
// TUT!!!!!!
    private boolean consoleMode(String line, boolean isRunning) throws IOException{
        String command = console.parseCommand(line);
        if (Config.EXIT_WORD.equalsIgnoreCase(command)) {
            return false;
        }
        if (Config.SCRIPT_WORD.equals(command)){
            scriptMode(userRequest.getArgs());
        }

        doRequest(line);
        return isRunning;
    }

    private void run(){
        try{
            boolean isRunning = true;
            network = new Network(Config.SERVER_ADDRESS, Config.SERVER_PORT);
            console.println("Connected to server");

            console.println("Enter command (or 'exit' to quit):");
            while (isRunning) {
                String line = console.nextLine();
                if(line == null){
                    continue;
                }
                userRequest = Console.parseLine(line);
                isRunning = consoleMode(userRequest.getCommand(), isRunning);
            }
            network.close();
        }catch (SocketException e){
            console.println("Server is temporarily unavailable");
            run();
        } catch(IOException e){
            console.println("\nClient stopped");
        }
    }
}
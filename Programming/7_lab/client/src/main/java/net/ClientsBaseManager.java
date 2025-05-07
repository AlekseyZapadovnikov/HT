package net;

import IO.Console;
import IO.Request;
import IO.Response;

import java.io.IOException;

public class ClientsBaseManager {
    private final Network network;
    private final Console console;

    public ClientsBaseManager(Network network, Console console) {
        this.network = network;
        this.console = console;
    }

    public Response checkClient(String[] args) throws IOException, ClassNotFoundException {
        Request request = new Request(args);
        console.setLogin(args[0]);
        network.write(request);
        Response response = (Response) network.read();
        return response;
    }

    public boolean parseClientStatus(String[] args) throws IOException, ClassNotFoundException {
        Response response = checkClient(args);
        console.println(response.getMessage());
        return response.isClientStatus();
    }
}

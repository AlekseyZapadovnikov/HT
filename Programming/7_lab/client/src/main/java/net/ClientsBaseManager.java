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

    public short checkClient(String[] args) throws IOException, ClassNotFoundException {
        Request request = new Request(args);
        network.write(request);
        Response response = (Response) network.read();
        return response.getClientStatus();
    }

    public boolean parseClientStatus(String[] args) throws IOException, ClassNotFoundException {
        boolean flag = false;
        switch (checkClient(args)) {
            case (0):
                console.println("Вы успешно подключены и можете приступать к работе");
                flag = true;
                break;
            case (1):
                console.println("Пользователя с таким login нет =(");
                break;
            case (2):
                console.println("неверный пароль");
                break;
        }
        return flag;
    }
}

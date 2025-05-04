package net;

import IO.Request;

public class ClientsBaseManager {
    private final String login;
    private final String password;
    private boolean flag;

    public ClientsBaseManager(String login, String password, String ans) {
        this.login = login;
        this.password = password;
        if (ans.equalsIgnoreCase("yes")) {
            flag = true;
        } else {
            flag = false;
        }
    }

    public Request getCheckInRequest() {
        return new Request(login, password, flag);
    }
}

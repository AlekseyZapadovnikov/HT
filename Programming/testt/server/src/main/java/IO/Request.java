package IO;

import itemsInArrea.Route;
import managers.NetworkMessage;

public class Request implements NetworkMessage {
    private String command;
    private String[] args;
    private Route route;
    private boolean containRoute = false;
    private boolean argsContain = false;
    private boolean isError = false;
    private Exception exception;

    public Request(String command, String[] args) {
        this.command = command;
        argsContain = true;
    }

    public Request(String command, Route route){
        this.command = command;
        this.route = route;
        containRoute = true;
        argsContain = true;
    }

    public Request(String command){
        this.command = command;
    }

    public Request(Exception e){
        isError = true;
        this.exception = e;
    }

    public String getCommand() {
        return command;
    }

    public String[] getArgs() {
        return args;
    }

    public boolean isContainRoute() {
        return containRoute;
    }

    public boolean isError() {
        return isError;
    }

    public Route getRoute() {
        return route;
    }

    public boolean isArgsContain() {
        return argsContain;
    }

    public Exception getException() {
        return exception;
    }
}

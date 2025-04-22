package IO;

import itemsInArrea.Route;

import java.io.Serializable;

public class Request implements Serializable {
    private String command;
    private String[] args;
    private Route route;
    private boolean containRoute = false;
    private boolean argsContain = false;
    private boolean isError = false;
    private Exception exception;
    private static final long serialVersionUID = 1L;

    public Request(String command, String[] args) {
        this.command = command;
        this.args    = args;
        this.argsContain = args != null && args.length > 0;
    }

    public Request(String command, Route route){
        this.command = command;
        this.route   = route;
        this.containRoute = route != null;
        this.argsContain  = false;
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

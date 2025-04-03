package IO;

import itemsInArrea.Route;
import managers.NetworkMessage;

public class Request implements NetworkMessage {
    private String command;
    private String[] args;
    private Route route;
    private boolean containRoute = false;
    private boolean argsContain = false;

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

    public String getCommand() {
        return command;
    }

    public String[] getArgs() {
        return args;
    }

    public boolean isContainRoute() {
        return containRoute;
    }

    public Route getRoute() {
        return route;
    }
}

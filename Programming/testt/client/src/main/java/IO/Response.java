package IO;

import itemsInArrea.Route;
import managers.NetworkMessage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Response implements NetworkMessage {
    private boolean isError = false;
    private Exception exception;
    private String errorDescription;
    private LinkedList<Route> routes;
    private Route route;
    private boolean isContainRoutes;
    private String commandName;
    private boolean isEmpty = false;
    private String message = "";
    ArrayList<String> info;

    public Response(Exception e, String description) {
        isError = true;
        exception = e;
        errorDescription = description;
    }

    public Response(String commandName, LinkedList<Route> routes) {
        this.routes = routes;
        this.commandName = commandName;
        isContainRoutes = true;
    }

    public Response(String commandName, ArrayList<String> info) {
        this.info = info;
        this.commandName = commandName;
    }

    public Response(String commandName, Route route) {
        isContainRoutes = true;
        this.route = route;

    }

    public Response(String commandName, String message) {
        this.commandName = commandName;
        this.message = message;
    }

    public Response() {
        isEmpty = true;
    }


    public Response(String message) {
        this.message = message;
    }

    @Override
    public String toString(){
        String s = "was executing command " + commandName + "\n";
        if (!isEmpty){
            if (isError){
                s += "an error occurs when executing the command \n" + errorDescription + "\n";
            } else {
                s += message;
                s += "\ncommand completed successfully";
            }
        }
        return s;
    }


    public boolean isError() {
        return isError;
    }

    public Exception getException() {
        return exception;
    }

    public String getExceptionMessage() {
        return exception.getMessage();
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public Route getRoute() {
        return route;
    }

    public boolean isContainRoutes() {
        return isContainRoutes;
    }

    public String getCommandName() {
        return commandName;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getInfo() {
        return info;
    }
}

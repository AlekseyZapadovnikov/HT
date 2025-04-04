package IO;

import itemsInArrea.Route;
import managers.NetworkMessage;

import java.util.LinkedList;
import java.util.List;

public class Response implements NetworkMessage {
    private boolean isError = false;
    private Exception exception;
    private String ErrorDescription;
    private List<Route> routes;
    private boolean isContainRoutes;
    private String commandName;

    public Response(Exception e, String description) {
        isError = true;
        exception = e;
        ErrorDescription = description;
    }

    public Response(String commandName, LinkedList<Route> routes) {
        routes = routes;
        this.commandName = commandName;
        isContainRoutes = true;
    }

    public Response(String commandName){
        this.commandName = commandName;
    }

}

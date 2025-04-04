package IO;

import itemsInArrea.Route;
import managers.NetworkMessage;

import javax.annotation.processing.RoundEnvironment;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Response implements NetworkMessage {
    private boolean isError = false;
    private Exception exception;
    private String ErrorDescription;
    private List<Route> routes;
    private Route route;
    private boolean isContainRoutes;
    private String commandName;
    private boolean isEmpty = false;
    private String message = "";
    List<String> info;

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

    public Response(String commandName, Route route){
        isContainRoutes = true;
        this.route = route;

    }

    public Response(String commandName, String message){
        this.commandName = commandName;
        this.message = message;
    }

    public Response(String name, ArrayList<String> lst){
        commandName = name;
        info = lst;
    }

    public Response(){
        isEmpty = true;
    };

    public Response(String message){
        this.message = message;
    }

}

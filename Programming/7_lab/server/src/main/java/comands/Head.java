package comands;

import IO.Response;
import managers.ColectionManager;

/**
 * The Head class extends the abstract Command class and prints the first element
 * of the collection.
 */
public class Head extends Command {
    /**
     * A manager to handle the collection of routes.
     */
    ColectionManager colectionManager;

    /**
     * Constructs a new Head command with the specified name, description, and collection manager.
     *
     * @param name             the name of the command
     * @param description      the description of what the command does
     * @param colectionManager the collection manager handling the routes
     */
    public Head(String name, String description, ColectionManager colectionManager) {
        super(name, description);
        this.colectionManager = colectionManager;
    }

    /**
     * Prints the first route in the collection if it exists; otherwise prints a message indicating
     * that the collection is empty.
     *
     * @param args command-line arguments (not used in this command)
     */
    @Override
    public Response execute(String[] args) {
        if (colectionManager.getRoutes().isEmpty()) {
            return new Response("collection is empty");
        } else {
            return new Response(super.name, colectionManager.getRoutes().get(0));
        }
    }
}
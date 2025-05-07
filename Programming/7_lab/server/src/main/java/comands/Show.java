package comands;

import IO.Response;
import itemsInArrea.Route;
import managers.ColectionManager;

import java.util.LinkedList;

/**
 * Command to display all Route objects from the collection.
 * <p>
 * It iterates through all routes maintained by the {@link ColectionManager}
 * and prints each route's detail on a new line.
 * </p>
 */
public class Show extends Command {
    public ColectionManager colectionManager;

    /**
     * Constructs a new Show command.
     *
     * @param name              the name of the command.
     * @param description       a brief description of what the command does.
     * @param colectionManager  the manager that holds the collection of routes.
     */
    public Show(String name, String description, ColectionManager colectionManager) {
        super(name, description);
        this.colectionManager = colectionManager;
    }

    /**
     * Executes the command: iterating over the collection of Route objects and
     * printing their details.
     *
     * @param args command-line arguments (not used in this command).
     */
    @Override
    public Response execute(String[] args) {
        return new Response(super.name, colectionManager.getRoutes());
    }
}

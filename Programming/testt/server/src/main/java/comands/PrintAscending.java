package comands;

import itemsInArrea.Route;
import managers.ColectionManager;

/**
 * The PrintAscending class extends the {@code Command} class and prints all routes
 * from the collection in ascending order.
 * <p>
 * This command retrieves the list of routes from the {@code ColectionManager},
 * sorts them in ascending order using their natural ordering, and prints each route.
 * </p>
 */
public class PrintAscending extends Command {
    /**
     * The collection manager that provides access to the list of routes.
     */
    private ColectionManager colectionManager;

    /**
     * Constructs a new PrintAscending command with the specified name, description,
     * and collection manager.
     *
     * @param name             the name of the command
     * @param description      a brief description of what the command does
     * @param colectionManager the collection manager containing the routes
     */
    public PrintAscending(String name, String description, ColectionManager colectionManager) {
        super(name, description);
        this.colectionManager = colectionManager;
    }

    /**
     * Executes the PrintAscending command.
     * <p>
     * This method sorts the routes in the collection in their natural (ascending) order
     * and outputs each route to the console.
     * </p>
     *
     * @param args an array of String arguments (not used in this command)
     */
    @Override
    public void execute(String[] args) {
        colectionManager.getroutes().sort(null);
        for (Route route : colectionManager.getroutes()) {
            System.out.println(route);
        }
    }
}
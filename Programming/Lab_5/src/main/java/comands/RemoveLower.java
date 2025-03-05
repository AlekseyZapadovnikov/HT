package comands;

import IO.ConsoleQuest;
import itemsInArrea.Route;
import managers.ColectionManager;

import java.util.Iterator;

/**
 * Command to remove all elements from the collection that are considered lower than a specified element.
 * <p>
 * This command interacts with the user by requesting a Route object using the {@link ConsoleQuest#askRoute(ColectionManager, long)}
 * method. It then iterates over the collection managed by {@link ColectionManager} and removes any object that is less than
 * the user-provided Route, based on the natural ordering defined by the {@link Route#compareTo(Route)} method.
 * </p>
 */
public class RemoveLower extends Command {
    private ColectionManager colMan;

    /**
     * Constructs a new RemoveLower command with the specified name, description, and collection manager.
     *
     * @param name        the name of the command.
     * @param description the description of the command.
     * @param colMan      the collection manager that maintains the routes.
     */
    public RemoveLower(String name, String description, ColectionManager colMan) {
        super(name, description);
        this.colMan = colMan;
    }

    /**
     * Executes the remove operation.
     * <p>
     * This method first prompts the user to provide a Route object by calling {@link ConsoleQuest#askRoute(ColectionManager, long)}.
     * It then iterates through the collection of Route objects and removes each object that is lower than the user-provided Route.
     * The removal is performed using {@link Iterator#remove()} to ensure safe modifications during iteration.
     * Finally, it prints a confirmation message upon successful completion.
     * </p>
     *
     * @param args command line arguments (not used in this command).
     */
    @Override
    public void execute(String[] args) {
        Route curRoute = ConsoleQuest.askRoute(colMan, -1L);

        Iterator<Route> it = colMan.getroutes().iterator();
        while (it.hasNext()) {
            Route route = it.next();
            if (curRoute.compareTo(route) > 0) {
                it.remove(); // using iterator.remove() for safe removal during iteration.
            }
        }
        System.out.println("All objects were successfully removed");
    }
}
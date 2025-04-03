package comands;

import managers.ColectionManager;
import itemsInArrea.Route;

/**
 * The CountByDistance class extends the <code>Command</code> class
 * and counts how many routes have a specified distance.
 */
public class CountByDistance extends Command {
    /**
     * The manager that holds and manages all routes.
     */
    ColectionManager colectionManager;

    /**
     * Constructs a new CountByDistance command with the given parameters.
     *
     * @param name             the name of the command
     * @param description      the description of the command
     * @param colectionManager a reference to the collection manager that holds routes
     */
    public CountByDistance(String name, String description, ColectionManager colectionManager) {
        super(name, description);
        this.colectionManager = colectionManager;
    }

    /**
     * Counts and prints the number of routes with the user-provided distance.
     * The distance is taken from the <code>args</code> array. If no distance is provided,
     * an error message is displayed.
     *
     * @param args array of string arguments; the last element should be the distance to count by
     */
    @Override
    public void execute(String[] args) {
        try {
            long dist = Long.parseLong(args[args.length]);

            long counter = 0L;
            for (Route route : colectionManager.getroutes()) {
                if (route.getDistance() == dist) {
                    counter++;
                }
            }
            System.out.println(counter);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("you haven`t enter parameter 'distance'");
        }
    }
}
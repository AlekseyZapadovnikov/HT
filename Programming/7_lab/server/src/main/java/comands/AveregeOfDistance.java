package comands;

import IO.Response;
import itemsInArrea.Route;
import managers.ColectionManager;

/**
 * The AveregeOfDistance class represents a command that calculates
 * the average distance by summing over all Route objects in the collection.
 * It extends the abstract Command class.
 */
public class AveregeOfDistance extends Command {
    /** A manager that handles all Route objects and operations on them. */
    ColectionManager colectionManager;

    /**
     * Constructs an AveregeOfDistance command with the specified name, description,
     * and collection manager.
     *
     * @param name             The name of the command
     * @param description      A short description of the command
     * @param colectionManager The collection manager holding Route objects
     */
    public AveregeOfDistance(String name, String description, ColectionManager colectionManager) {
        super(name, description);
        this.colectionManager = colectionManager;
    }

    /**
     * Calculates the average distance of all Route objects contained
     * in the provided ColectionManager, then prints the result.
     *
     * @param args Additional arguments for the command (not used here)
     */
    @Override
    public Response execute(String[] args) {
        long averDist = 0L;
        long elemAmount = colectionManager.getRoutes().size();

        for (Route route : colectionManager.getRoutes()) {
            averDist += route.getDistance() / elemAmount;
        }
        Long newAverageDist = (Long) averDist;
        return new Response(super.name, "Average distance = " + newAverageDist.toString());
    }
}
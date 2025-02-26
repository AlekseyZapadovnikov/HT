package comands;

import IO.ConsoleQuest;
import managers.ColectionManager;

/**
 * Command to update an element in the collection.
 * <p>
 * This command updates an existing element in the collection by prompting
 * the user to provide a new Route object using {@link ConsoleQuest#askRoute(ColectionManager, long)}.
 * The update is performed for the element with the specified identifier.
 * </p>
 */
public class Update extends Command {
    private final ColectionManager colectionManager;

    /**
     * Constructs a new Update command.
     *
     * @param name             the name of the command.
     * @param description      a short description of what the command does.
     * @param colectionManager the collection manager that holds the elements to be updated.
     */
    public Update(String name, String description, ColectionManager colectionManager) {
        super(name, description);
        this.colectionManager = colectionManager;
    }

    /**
     * Executes the update command.
     * <p>
     * The command expects the first argument to be a valid long value which represents the id
     * of the element to update. It then asks the user for new data via {@link ConsoleQuest#askRoute(ColectionManager, long)}
     * and updates the corresponding element in the collection.
     * </p>
     *
     * @param args command-line arguments, where args[0] must be the identifier of the element to update.
     */
    @Override
    public void execute(String[] args) {
        long id = Long.parseLong(args[0]);
        colectionManager.update(ConsoleQuest.askRoute(colectionManager, id));
    }
}
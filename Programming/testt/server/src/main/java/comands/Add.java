package comands;


import itemsInArrea.Route;
import managers.ColectionManager;

/**
 * The Add class represents a command that creates a new object and adds it to the collection.
 * Extends the abstract Command class.
 */
public class Add extends Command {
    private ColectionManager colectionManager;

    /**
     * Constructs a new Add command.
     *
     * @param name             The name of the command
     * @param description      A short description of the command
     * @param colectionManager An instance of ColectionManager that manages the collection
     */
    public Add(String name, String description, ColectionManager colectionManager) {
        super(name, description);
        this.colectionManager = colectionManager;
    }

    /**
     * Executes the command by prompting the user to create a new object
     * and adding it to the collection.
     *
     * @param args String array of arguments (not used in this command)
     */
    @Override
    public void execute(Object args) {
        Route route = (Route) args;
            colectionManager.add(route);
    }
}
package comands;

import IO.Response;
import managers.ColectionManager;

/**
 * The Info command class extends the {@code Command} class and displays information
 * about the collection managed by the provided {@code ColectionManager}.
 * <p>
 * This command prints a predefined message and displays the details of the collection.
 * </p>
 */
public class Info extends Command {
    /**
     * The collection manager that maintains the collection of items.
     */
    ColectionManager colectionManager;

    /**
     * Constructs a new Info command with the specified name, description, and collection manager.
     *
     * @param name             the name of the command
     * @param description      a brief description of what the command does
     * @param colectionManager the collection manager that handles the collection to be displayed
     */
    public Info(String name, String description, ColectionManager colectionManager) {
        super(name, description);
        this.colectionManager = colectionManager;
    }

    /**
     * Executes the Info command.
     * <p>
     * This method prints a header message and then outputs information about the collection,
     * as provided by the {@code ColectionManager}.
     * </p>
     *
     * @param args an array of arguments (not used in this command)
     */
    @Override
    public Response execute(String[] args) {
        return new Response(super.name, colectionManager.toString());
    }
}
package comands;

import IO.Response;
import managers.ColectionManager;

/**
 * The Clear command class that clears the collection.
 * <p>
 * This command delegates the clear operation to the provided {@link ColectionManager}.
 * </p>
 */
public class Clear extends Command {
    private final ColectionManager colectionManager;

    /**
     * Constructs a new Clear command.
     *
     * @param name              the name of the command.
     * @param description       a brief description of what the command does.
     * @param colectionManager  the collection manager responsible for managing the collection.
     */
    public Clear(String name, String description, ColectionManager colectionManager) {
        super(name, description);
        this.colectionManager = colectionManager;
    }

    /**
     * Executes the clear command by clearing the collection managed by {@link ColectionManager}.
     *
     * @param args not used for this command.
     */
    @Override
    public Response execute(String[] args, String userLogin) {
        return new Response("you can`t clear collection, because not all objects belong to you");
    }
}
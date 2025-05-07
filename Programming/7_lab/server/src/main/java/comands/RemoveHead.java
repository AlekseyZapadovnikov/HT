package comands;

import IO.Response;
import managers.ColectionManager;

/**
 * The RemoveHead command removes the first element from the collection.
 * <p>
 * It prints the head element before removing it. If the collection is empty, it outputs an appropriate message.
 * </p>
 */
public class RemoveHead extends Command {
    /**
     * The collection manager that handles the routes.
     */
    ColectionManager colectionManager;

    /**
     * Constructs a new RemoveHead command with the specified name, description, and collection manager.
     *
     * @param name             the name of the command
     * @param description      a brief description of what the command does
     * @param colectionManager the collection manager handling the collection of routes
     */
    public RemoveHead(String name, String description, ColectionManager colectionManager) {
        super(name, description);
        this.colectionManager = colectionManager;
    }

    /**
     * Executes the RemoveHead command.
     * <p>
     * If the collection is not empty, it prints the first element and then removes it.
     * If the collection is empty, it prints a message indicating that the collection is empty.
     * </p>
     *
     * @param args an array of command arguments (not used in this command)
     */
    @Override
    public Response execute(String[] args) {
        return new Response("вы не можете удалять не ваши объекты");
    }
}
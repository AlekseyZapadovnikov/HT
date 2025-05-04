package comands;

import IO.Response;
import managers.ColectionManager;

/**
 * The RemoveById command extends the {@code Command} class to remove an object from
 * the collection by its unique identifier.
 * <p>
 * It parses the provided ID from the command arguments and removes the object if it exists.
 * Appropriate error messages are displayed for invalid input, missing arguments, or if the object
 * with the specified ID does not exist.
 * </p>
 */
public class RemoveById extends Command {
    /**
     * The collection manager responsible for handling the collection operations.
     */
    ColectionManager colMan;

    /**
     * Constructs a RemoveById command with the specified name, description, and collection manager.
     *
     * @param name        the name of the command
     * @param description a short description of the command's functionality
     * @param colMan      the collection manager to manage the objects in the collection
     */
    public RemoveById(String name, String description, ColectionManager colMan) {
        super(name, description);
        this.colMan = colMan;
    }

    /**
     * Executes the RemoveById command.
     * <p>
     * This method attempts to parse the first argument as a long value representing the ID.
     * If the ID exists in the collection, it is removed and a success message is printed.
     * Otherwise, a message indicating that no element with such an ID exists is printed.
     * Error messages are displayed if the input is invalid or if the ID is not provided.
     * </p>
     *
     * @param args an array of command arguments, where the first element should be the ID
     */
    @Override
    public Response execute(String[] args) {
        Response response;
        try {
            long id = Long.parseLong(args[0]);
            if (colMan.isContainId(id)) {
                colMan.remove(id);
                response = new Response(super.name, "Obj was removed successfully");
            } else {
                response = new Response(super.name, "There is no element with such ID");
            }
        } catch (NumberFormatException e) {
            return new Response(e,"You entered ID incorrectly, ID - type long");
        } catch (IndexOutOfBoundsException e) {
            return new Response(e,"You haven`t entered ID");
        } catch (Exception e) {
            return new Response(e,"smth went wrong =(");
        }
        return response;
    }
}
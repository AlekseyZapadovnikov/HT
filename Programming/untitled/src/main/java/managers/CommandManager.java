package managers;

import comands.Command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Manages command objects and their execution history.
 * <p>
 * This class allows adding {@link Command} instances, retrieving them by name,
 * and storing a limited history of executed command names.
 * </p>
 */
public class CommandManager {

    /**
     * A mapping from command names to their corresponding {@link Command} objects.
     */
    HashMap<String, Command> commands = new HashMap<>();

    /**
     * A history of command names (in the order they were added),
     * limited to the last 10 entries.
     */
    ArrayList<String> history = new ArrayList<>();

    /**
     * Retrieves a {@link Command} from the manager by its name.
     *
     * @param name the name of the command
     * @return the {@link Command} instance, or {@code null} if not found
     */
    public Command getByName(String name) {
        return commands.get(name);
    }

    /**
     * Adds a new {@link Command} to the manager's collection.
     * <p>
     * The command is stored using its name (retrieved by {@code command.getName()})
     * as the key in the internal map.
     * </p>
     *
     * @param command the {@link Command} to add
     */
    public void addCommand(Command command) {
        commands.put(command.getName(), command);
    }

    /**
     * Returns a set of all command names currently stored in this manager.
     *
     * @return a {@link Set} of {@link String} representing command names
     */
    public Set<String> getCommands() {
        return commands.keySet();
    }

    /**
     * Returns the current history of executed command names.
     * <p>
     * The list is maintained in the order commands were added,
     * up to a maximum of 10 entries.
     * </p>
     *
     * @return an {@link ArrayList} of command names
     */
    public ArrayList<String> getHistory() {
        return history;
    }

    /**
     * Adds a command name to the history.
     * <p>
     * If the history already has 10 entries, the oldest one (index 0) is removed.
     * </p>
     *
     * @param command the name of the command to add to history
     */
    public void addToHistory(String command) {
        if (history.size() == 10) {
            history.remove(history.get(0));
        }
        history.add(command);
    }

    /**
     * Retrieves a {@link Command} by its name.
     * <p>
     * This method is functionally the same as {@link #getByName(String)}
     * but named differently for convenience.
     * </p>
     *
     * @param name the name of the command
     * @return the {@link Command} instance, or {@code null} if not found
     */
    public Command getCommandByName(String name) {
        return commands.get(name);
    }
}
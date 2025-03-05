package comands;

/**
 * An abstract command with a name and description.
 * <p>
 * This class serves as a blueprint for specific commands, requiring subclasses to implement
 * the {@link #execute(String[])} method.
 * </p>
 */
public abstract class Command {
    private final String name;
    private final String description;

    /**
     * Constructs a new Command with the specified name and description.
     *
     * @param name        the name of the command.
     * @param description a brief description of the command.
     */
    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Executes the command with the provided arguments.
     *
     * @param args an array of String arguments.
     */
    public abstract void execute(String[] args);

    /**
     * Returns the name and usage of the command.
     *
     * @return the command's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the description of the command.
     *
     * @return the command's description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Checks if this command is equal to another object.
     * Two commands are considered equal if their names and descriptions are equal.
     *
     * @param obj the object to compare to.
     * @return true if the commands are equal; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Command command = (Command) obj;
        return name.equals(command.name) && description.equals(command.description);
    }

    /**
     * Returns the hash code value for this command.
     *
     * @return the hash code, based on the command's name and description.
     */
    @Override
    public int hashCode() {
        return name.hashCode() + description.hashCode();
    }

    /**
     * Returns a string representation of the command.
     *
     * @return a string in the format "name : description".
     */
    @Override
    public String toString() {
        return name + " : " + description;
    }
}
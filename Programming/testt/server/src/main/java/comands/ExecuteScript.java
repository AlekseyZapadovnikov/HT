package comands;

import IO.Response;
import managers.CommandManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The ExecuteScript class extends <code>Command</code> and reads commands from a specified file,
 * executing them through the provided <code>CommandManager</code>.
 */
public class ExecuteScript extends Command {
    /**
     * A reference to the <code>CommandManager</code> that provides access
     * to commands by their names.
     */
    CommandManager commandManager;

    /**
     * Constructs an <code>ExecuteScript</code> command with the specified parameters.
     *
     * @param name            the name of the command
     * @param description     the description of the command
     * @param commandManager  the manager that will be used to retrieve and execute commands
     */
    public ExecuteScript(String name, String description, CommandManager commandManager) {
        super(name, description);
        this.commandManager = commandManager;
    }

    /**
     * Executes the <code>ExecuteScript</code> command by reading a file from
     * <code>src/main/resources</code> and processing each line as a separate command call.
     * <p>
     *     Each line in the file is split by spaces: the first token is the command name,
     *     and subsequent tokens are arguments.
     * </p>
     *
     * @param args a single-element array containing the filename to read
     */
    @Override
    public Response execute(String[] args) {
        File file = null;
        try {
            // Attempt to create a File object with the given filename
            file = new File("src\\main\\resources\\" + args[0]);
        } catch (IndexOutOfBoundsException e) {
            return  new Response(e, "you haven`t enter a file name");
        }
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                try {
                    String[] line = scanner.nextLine().split(" ");
                    String[] arguments = new String[line.length - 1];
                    for (int i = 1; i < line.length; i++) {
                        arguments[i - 1] = line[i];
                    }
                    Command command = commandManager.getCommandByName(line[0].trim());
                    command.execute(arguments);
                    System.out.println("\n");
                } catch (Exception ex) {
                    return new Response(ex, "uncorrected command");
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            return new Response(e, "File not found \n" +
                    "File have to be in 'src\\main\\resources\\'");
        }
    }
}
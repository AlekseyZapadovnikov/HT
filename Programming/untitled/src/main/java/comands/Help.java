package comands;

import managers.ColectionManager;
import managers.CommandManager;

/**
 * The Help class extends the {@link Command} class and provides functionality
 * to display help information about all available commands.
 */
public class Help extends Command {
    /**
     * A reference to the CommandManager which manages all available commands.
     */
    CommandManager commandManager;

    /**
     * Constructs a Help command with the given name, description, and CommandManager.
     *
     * @param name            the name of the command
     * @param description     a brief description of the command
     * @param commandManager  the CommandManager object that manages the available commands
     */
    public Help(String name, String description, CommandManager commandManager) {
        super(name, description);
        this.commandManager = commandManager;
    }

    /**
     * Executes the Help command by printing out information about each available command.
     * It iterates through the list of commands provided by the CommandManager and prints their details.
     *
     * @param args an array of String arguments (not used by this command)
     */
    @Override
    public void execute(String[] args){
        for (String comand : commandManager.getCommands()){
            System.out.println(commandManager.getCommandByName(comand));
        }
    }
}
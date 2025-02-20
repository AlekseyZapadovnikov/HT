package comands;

import IO.Console;
import managers.ColectionManager;
import managers.CommandManager;

public class Help extends Command {
    CommandManager commandManager;

    public Help(String name, String description, CommandManager commandManager) {
        super(name, description);
        this.commandManager = commandManager;
    }


    @Override
    public void execute(String[] args){
        for (String comand : commandManager.getCommands()){
            System.out.println((commandManager.getCommandByName(comand)));
        }
    }
}

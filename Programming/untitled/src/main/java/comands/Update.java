package comands;

import IO.Console;
import IO.ConsoleQuest;
import managers.ColectionManager;

public class Update extends Command{
    ColectionManager colectionManager;
    Console console;

    public Update(String name, String description, ColectionManager colectionManager, Console console) {
        super(name, description);
        this.colectionManager = colectionManager;
        this.console = console;
    }

    @Override
    public void execute(String[] args){
        long id = Long.parseLong(args[0]);
        try {
            colectionManager.update(ConsoleQuest.askRoute(console, colectionManager, id));
        } catch (ConsoleQuest.AskBreak e) {
            throw new RuntimeException(e);
        }
    }
}

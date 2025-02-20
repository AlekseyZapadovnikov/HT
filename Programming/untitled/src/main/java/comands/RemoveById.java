package comands;

import IO.Console;
import managers.ColectionManager;

public class RemoveById extends Command{
    ColectionManager colMan;
    Console console;

    public RemoveById(String name, String description, ColectionManager colMan, Console console) {
        super(name, description);
        this.colMan = colMan;
        this.console = console;
    }

    @Override
    public void execute(String[] args) {
        long id = Long.parseLong(args[0]);
        colMan.remove(id);
    }
}

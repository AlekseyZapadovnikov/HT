package comands;

import IO.ConsoleQuest;
import managers.ColectionManager;

public class Update extends Command{
    ColectionManager colectionManager;

    public Update(String name, String description, ColectionManager colectionManager) {
        super(name, description);
        this.colectionManager = colectionManager;
    }

    @Override
    public void execute(String[] args){
        long id = Long.parseLong(args[0]);
        colectionManager.update(ConsoleQuest.askRoute(colectionManager, id));
    }
}

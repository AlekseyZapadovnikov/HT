package comands;

import IO.Console;
import managers.ColectionManager;

public class Clear extends Command{
    ColectionManager colectionManager;

    public Clear(String name, String description, ColectionManager colectionManager) {
        super(name, description);
        this.colectionManager = colectionManager;
    }

    @Override
    public void execute(String[] args) {
        colectionManager.clear();
    }
}

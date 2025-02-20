package comands;

import IO.Console;
import itemsInArrea.Route;
import managers.ColectionManager;

public class Show extends Command{
    private ColectionManager colectionManager;

    public Show(String name, String description, ColectionManager colectionManager) {
        super(name, description);
        this.colectionManager = colectionManager;
    }

    @Override
    public void execute(String[] args) {
        for (Route route : colectionManager.getroutes()){
            System.out.println(route + "\n");
        }
    }
}

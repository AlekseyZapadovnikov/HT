package comands;

import itemsInArrea.Route;
import managers.ColectionManager;

public class PrintAscending extends Command{
    private ColectionManager colectionManager;

    public PrintAscending(String name, String description, ColectionManager colectionManager) {
        super(name, description);
        this.colectionManager = colectionManager;
    }

    @Override
    public void execute(String[] args) {
        colectionManager.getroutes().sort(null);
        for (Route route : colectionManager.getroutes()){
            System.out.println(route);
        }
    }
}

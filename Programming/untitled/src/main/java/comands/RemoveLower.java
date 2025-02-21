package comands;

import IO.ConsoleQuest;
import itemsInArrea.Route;
import managers.ColectionManager;

import java.util.Iterator;

public class RemoveLower extends Command {
    private ColectionManager colMan;

    public RemoveLower(String name, String description, ColectionManager colMan) {
        super(name, description);
        this.colMan = colMan;
    }

    @Override
    public void execute(String[] args) {
        Route curRoute = ConsoleQuest.askRoute(colMan, -1L);

        Iterator<Route> it = colMan.getroutes().iterator();
        while (it.hasNext()) {
            Route route = it.next();
            if (curRoute.compareTo(route) > 0) {
                it.remove(); // используем метод iterator.remove()
            }
        }
        System.out.println("All Objects was successfully removed");
    }
}

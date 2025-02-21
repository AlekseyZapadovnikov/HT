package comands;

import IO.ConsoleQuest;
import itemsInArrea.Route;
import managers.ColectionManager;

public class RemoveLower extends Command {
    private ColectionManager colMan;

    public RemoveLower(String name, String description, ColectionManager colMan) {
        super(name, description);
        this.colMan = colMan;
    }

    @Override
    public void execute(String[] args) {
        Route curRoute = ConsoleQuest.askRoute(colMan, -1L);

        for (Route route : colMan.getroutes()) {
            if (curRoute.compareTo(route) > 0) {
                colMan.remove(route.getId());
            }
        }
    }
}

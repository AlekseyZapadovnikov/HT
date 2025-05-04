package comands;

import IO.Response;
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
    public Response execute(Object obj) {
        Route curRoute = (Route) obj;

        Iterator<Route> it = colMan.getroutes().iterator();
        while (it.hasNext()) {
            Route route = it.next();
            if (curRoute.compareTo(route) > 0) {
                it.remove(); // using iterator.remove() for safe removal during iteration.
            }
        }
        return new Response(super.name, "All objects were successfully removed");
    }
}
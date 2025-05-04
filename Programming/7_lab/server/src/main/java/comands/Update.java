package comands;


import IO.Response;
import itemsInArrea.Route;
import managers.ColectionManager;


public class Update extends Command {
    private final ColectionManager colectionManager;

    /**
     * Constructs a new Update command.
     *
     * @param name             the name of the command.
     * @param description      a short description of what the command does.
     * @param colectionManager the collection manager that holds the elements to be updated.
     */
    public Update(String name, String description, ColectionManager colectionManager) {
        super(name, description);
        this.colectionManager = colectionManager;
    }


    @Override
    public Response execute(Object obj) {
        Route curRoute = (Route) obj;
        long id = curRoute.getId();
        colectionManager.update(curRoute);
        return new Response(super.name, "collection was succsessfuly update");
    }
}
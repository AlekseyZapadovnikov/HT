package comands;

import IO.Response;
import itemsInArrea.Route;
import managers.ColectionManager;
import comands.sqlCommands.sqlAddRoute;

/**
 * The Add class represents a command that creates a new object and adds it to the collection.
 * Extends the abstract Command class.
 */
public class Add extends Command {
    private ColectionManager colectionManager;
    private sqlAddRoute addRouteCmd;

    /**
     * Constructs a new Add command.
     *
     * @param name             The name of the command
     * @param description      A short description of the command
     * @param colectionManager An instance of ColectionManager that manages the collection
     */
    public Add(String name, String description, ColectionManager colectionManager) {
        super(name, description);
        this.colectionManager = colectionManager;
        this.addRouteCmd = new sqlAddRoute("add_route");
    }

    /**
     * Executes the command by prompting the user to create a new object
     * and adding it to the collection and the database.
     *
     * @param args     The route object to be added
     * @param userName The username of the person adding the route
     */
    @Override
    public Response execute(Object args, String userName) {
        Route route = (Route) args;

        // Добавляем маршрут в БД
        boolean dbSuccess = addRouteCmd.execute(route, userName);
        System.out.println(dbSuccess);
        if (!dbSuccess) {
            return new Response(super.name, "Failed to add route to the database");
        }

        // Если добавление в БД прошло успешно, добавляем в коллекцию
        colectionManager.add(route);

        return new Response(super.name, "Route added to collection and database");
    }
}

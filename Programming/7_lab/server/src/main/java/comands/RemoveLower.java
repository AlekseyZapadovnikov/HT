package comands;

import IO.Response;
import itemsInArrea.Route;
import managers.ColectionManager;
import comands.sqlCommands.sqlRemoveRoute;

import java.util.Iterator;

/**
 * Команда для удаления всех маршрутов, меньших заданного.
 * Удаление сначала выполняется в базе данных, и только при успешном удалении
 * маршрут удаляется из коллекции.
 */
public class RemoveLower extends Command {
    private ColectionManager colMan;
    private sqlRemoveRoute dbRemoveCmd;

    public RemoveLower(String name, String description, ColectionManager colMan) {
        super(name, description);
        this.colMan = colMan;
        // Инициализируем команду для удаления маршрута из БД
        this.dbRemoveCmd = new sqlRemoveRoute("remove_route");
    }

    @Override
    public Response execute(Object obj) {
        if (!(obj instanceof Route)) {
            return new Response(super.name, "Invalid object type. Expected Route.");
        }
        Route curRoute = (Route) obj;
        int removedCount = 0;

        Iterator<Route> it = colMan.getRoutes().iterator();
        while (it.hasNext()) {
            Route route = it.next();
            // Удаляем только маршруты, которые меньше текущего
            if (curRoute.compareTo(route) > 0) {
                long routeId = route.getId();
                String ownerLogin = route.getUserLogin();
                // Сначала удаляем из базы данных
                boolean dbDeleted = dbRemoveCmd.execute("route", routeId, ownerLogin);
                if (dbDeleted) {
                    // При успешном удалении из БД удаляем из коллекции
                    it.remove();
                    removedCount++;
                } else {
                    // Можно логировать или накапливать информацию об ошибках удаления
                    System.out.println("Failed to remove route with ID=" + routeId + " from database. Skipping collection removal.");
                }
            }
        }

        String message = String.format("Удалено объектов: %d", removedCount);
        return new Response(super.name, message);
    }
}
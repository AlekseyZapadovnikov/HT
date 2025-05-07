package comands;

import IO.Response;
import managers.ColectionManager;
import comands.sqlCommands.sqlRemoveRoute;

/**
 * Команда для удаления объекта по ID.
 * Удаление сначала выполняется в базе данных, и только при успешном удалении
 * объект удаляется из коллекции.
 */
public class RemoveById extends Command {
    private ColectionManager colMan;
    private sqlRemoveRoute dbRemoveCmd;

    public RemoveById(String name, String description, ColectionManager colMan) {
        super(name, description);
        this.colMan = colMan;
        this.dbRemoveCmd = new sqlRemoveRoute("remove_route");
    }

    /**
     * Выполняет команду RemoveById.
     * @param args  массив аргументов, где args[0] должен быть ID
     * @param inputLogin логин текущего пользователя
     * @return ответ с результатом операции
     */
    @Override
    public Response execute(String[] args, String inputLogin) {
        try {
            if (args.length == 0) {
                return new Response(super.name, "You haven't entered ID");
            }
            long id = Long.parseLong(args[0]);
            if (!colMan.isContainId(id)) {
                return new Response(super.name, "There is no element with such ID");
            }
            boolean dbDeleted = dbRemoveCmd.execute("route", id, inputLogin);
            if (!dbDeleted) {
                return new Response(super.name, "Failed to remove object with ID=" + id + " from database");
            }
            colMan.remove(id); //тольуо если успех
            return new Response(super.name, "Object was removed successfully");
        } catch (NumberFormatException e) {
            return new Response(e, "You entered ID incorrectly, ID must be a long");
        } catch (Exception e) {
            return new Response(e, "Something went wrong =(");
        }
    }
}
package comands;

import IO.Console;
import IO.ConsoleQuest;
import itemsInArrea.Route;
import managers.ColectionManager;

public class RemoveLower extends Command {
    private ColectionManager colMan;
    private Console console;

    public RemoveLower(String name, String description, ColectionManager colMan, Console cons) {
        super(name, description);
        this.colMan = colMan;
    }

    @Override
    public void execute(String[] args) {
        try {
            Route curRoute = ConsoleQuest.askRoute(console, colMan, -1L);

            for (Route route : colMan.getroutes()) {
                if (curRoute.compareTo(route) > 0) {
                    colMan.remove(route.getId());
                }
            }

        } catch (ConsoleQuest.AskBreak e) {
            System.out.println("failed to create an bject");
            throw new RuntimeException(e);
        }
    }
}

package comands;

import IO.ConsoleQuest;
import managers.ColectionManager;

public class Add extends Command{
    private ColectionManager colectionManager;

    public Add(String name, String description, ColectionManager colectionManager) {
        super(name, description);
        this.colectionManager = colectionManager;
    }

    @Override
    public void execute(String[] args) {
        try {
            colectionManager.add(ConsoleQuest.askRoute(colectionManager, -1));
        } catch (Exception e) {
            System.out.println("произошла ошибка при создании объекта");
            ConsoleQuest.exit.execute();
        }
    }
}

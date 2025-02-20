package comands;

import IO.Console;
import IO.ConsoleQuest;
import managers.ColectionManager;

public class Add extends Command{
    private ColectionManager colectionManager;
    Console console;
    ConsoleQuest consQ;

    public Add(String name, String description, ColectionManager colectionManager, Console console, ConsoleQuest consQ) {
        super(name, description);
        this.colectionManager = colectionManager;
        this.console = console;
        this.consQ = consQ;
    }

    @Override
    public void execute(String[] args){
        try {
            colectionManager.add(ConsoleQuest.askRoute(console, colectionManager, -1));
        } catch (ConsoleQuest.AskBreak e) {
            throw new RuntimeException(e); //так, я не понимаю немного зачем это выбрасывать, ну потом разберёмся
        }
    }
}

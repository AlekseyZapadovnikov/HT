package comands;

import IO.Console;
import managers.ColectionManager;

public class Info extends Command{
    ColectionManager colectionManager;

    public Info(String name, String description, ColectionManager colectionManager) {
        super(name, description);
        this.colectionManager = colectionManager;
    }

    @Override
    public void execute(String[] args) {
        System.out.println(("Вывожу информацию о коллекции: "));
        System.out.println((colectionManager));
    }

}

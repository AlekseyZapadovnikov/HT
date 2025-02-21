package comands;

import IO.RouteXMLWriter;
import managers.ColectionManager;

import java.io.IOException;

public class Save extends Command{
    RouteXMLWriter saver;
    ColectionManager colectionManager;

    public Save(String name, String description, RouteXMLWriter saver, ColectionManager colectionManager) {
        super(name, description);
        this.saver = saver;
        this.colectionManager = colectionManager;
    }

    @Override
    public void execute(String[] args) {
        System.out.println("Данные будут сохранены в файл Saves.xml");

        try {
            saver.writeRoutes(colectionManager.getroutes());
            System.out.println("Данные успешно сохранены");
        } catch (IOException e) {
            System.out.println("Возникли проблемы с записью в файл");
            throw new RuntimeException(e);
        }
    }
}

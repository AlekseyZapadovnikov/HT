package comands;

import managers.ColectionManager;
import itemsInArrea.Route;

public class CountByDistance extends Command {
    ColectionManager colectionManager;

    public CountByDistance(String name, String description, ColectionManager colectionManager) {
        super(name, description);
        this.colectionManager = colectionManager;
    }

    @Override
    public void execute(String[] args) {
        long dist = Long.parseLong(args[args.length]);
        long counter = 0L;
        for (Route route : colectionManager.getroutes()){
            if (route.getDistance() == dist){
                counter++;
            }
        }
        System.out.println(counter);
    }
}

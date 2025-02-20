package comands;

import itemsInArrea.Route;
import managers.ColectionManager;

public class AveregeOfDistance extends Command{
    ColectionManager colectionManager;

    public AveregeOfDistance(String name, String description, ColectionManager colectionManager) {
        super(name, description);
        this.colectionManager = colectionManager;
    }

    @Override
    public void execute(String[] args) {
        long averDist = 0L;
        long elemAmount = colectionManager.getroutes().size();

        for (Route route : colectionManager.getroutes()){
            averDist += route.getDistance() / elemAmount;
        }
        System.out.println("Average distance = " + averDist);
    }
}

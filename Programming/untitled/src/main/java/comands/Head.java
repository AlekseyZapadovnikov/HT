package comands;

import managers.ColectionManager;

public class Head extends Command{
    ColectionManager colectionManager;

    public Head(String name, String description, ColectionManager colectionManager) {
        super(name, description);
        this.colectionManager = colectionManager;
    }

    @Override
    public void execute(String[] args) {
        if (colectionManager.getroutes().isEmpty()){
            System.out.println("Collection is empty");
        } else{
            System.out.println(colectionManager.getroutes().get(0));
        }
    }
}

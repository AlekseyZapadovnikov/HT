package comands;

import managers.ColectionManager;

public class RemoveHead extends Command{
    ColectionManager colectionManager;

    public RemoveHead(String name, String description, ColectionManager colectionManager) {
        super(name, description);
        this.colectionManager = colectionManager;
    }

    @Override
    public void execute(String[] args) {
        if (colectionManager.getroutes().isEmpty()){
            System.out.println("Collection is empty");
        } else{
            System.out.println(colectionManager.getroutes().get(0));
            colectionManager.remove(colectionManager.getroutes().get(0).getId());
        }
    }
}

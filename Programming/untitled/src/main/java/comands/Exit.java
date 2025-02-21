package comands;

public class Exit extends Command{

    public Exit(String name, String description) {
        super(name, description);
    }

    @Override
    public void execute(String[] args) {
        System.exit(0);
    }

    public void execute(){
        System.exit(0);
    }
}

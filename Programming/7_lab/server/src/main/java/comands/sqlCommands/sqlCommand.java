package comands.sqlCommands;


public abstract class sqlCommand {
    String name;

    public sqlCommand(String name){
        this.name = name;
    }

    public boolean execute(String tableName, Object param){return true;}
}

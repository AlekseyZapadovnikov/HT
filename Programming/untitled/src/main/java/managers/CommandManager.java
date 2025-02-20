package managers;

import comands.Command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Управляет командами
 */
public class CommandManager {
    HashMap<String, Command> commands = new HashMap<>();
    ArrayList<String> history = new ArrayList<>();

    public Command getByName(String name){
        return commands.get(name);
    }

    /**
     * Добавление команды
     * имя команды
     * @param command объект команды
     */
    public void addCommand(Command command){
        commands.put(command.getName(), command);
    }

    public Set<String> getCommands(){
        return commands.keySet();
    }

    public ArrayList<String> getHistory(){
        return history;
    }

    /**
     * Добавляет команд в историю
     * @param command - команда для добавления
     */
    public void addToHistory(String command){
        if(history.size() == 10){
            history.remove(history.get(0));
        }
        history.add(command);
    }

    public Command getCommandByName(String name){
        return commands.get(name);
    }
}
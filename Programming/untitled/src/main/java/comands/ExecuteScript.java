package comands;

import managers.CommandManager;

import javax.sound.midi.Soundbank;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ExecuteScript extends Command{
    CommandManager commandManager;

    public ExecuteScript(String name, String description, CommandManager commandManager) {
        super(name, description);
        this.commandManager = commandManager;
    }

    @Override
    public void execute(String[] args) {
        File file = new File(args[0]);
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()){
                try {
                    String[] line = scanner.nextLine().split(" ");
                    String[] arguments = new String[line.length - 1];
                    for (int i = 1; i < line.length; i++){
                        arguments[i-1] = line[i];
                    }
                    Command command = commandManager.getCommandByName(line[0].trim());
                    command.execute(arguments);
            } catch (Exception ex){
                    System.out.println("uncorrected command");
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            throw new RuntimeException(e);
        }
    }
}

package org.example;

import managers.ColectionManager;
import managers.CommandManager;
import managers.Runner;

import java.io.IOException;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        Runner runner = new Runner();
        CommandManager cm = runner.getCommandManager();
        ColectionManager clm = runner.getColectionManager();
        runner.run();
        while (true) {
            System.out.println("\nenter command");
            String[] line = scan.nextLine().split(" ");
            String[] arg = new String[line.length - 1];
            for (int i = 1; i < line.length; i++) {
                arg[i - 1] = line[i];
            }
            String commandName = line[0];
            if (cm.getCommands().contains(commandName)) {
                cm.getCommandByName(commandName).execute(arg);
            } else {
                System.out.println("we haven`t this command");
            }
        }
    }
}
package IO;

import itemsInArrea.Route;
import managers.NetworkMessage;

import javax.sound.midi.Soundbank;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Scanner;

public class Console extends ConsoleQuest {

    private static final String[] routeCommand = new String[]{"add", "update"};
    Scanner scanner = getScanner();

    public NetworkMessage readCommand() {
        String[] input = scanner.nextLine().toLowerCase().split(" ");
        NetworkMessage request;

        if (Arrays.asList(routeCommand).contains(input[0])) {
            Route route = askRoute();
            request = new Request(input[0], route);
        } else {
            if (input.length > 1) {
                String[] args = new String[input.length - 1];
                for (int i = 1; i < input.length; i++) {
                    args[i - 1] = input[i];
                }
                request = new Request(input[0], args);
            }
            request = new Request(input[0]);
        }
        return request;
    }
    public void println(String message){
        System.out.println(message);
    }
}
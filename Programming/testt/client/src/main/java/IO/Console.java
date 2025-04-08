package IO;

import itemsInArrea.Route;

import java.util.Arrays;
import java.util.Scanner;

public class Console extends ConsoleQuest {

    private static final String[] routeCommand = new String[]{"add", "update"};
    Scanner scanner = getScanner();

    public Request readCommand() {
        String[] input = scanner.nextLine().toLowerCase().split(" ");
        Request request;

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
            } else {
                request = new Request(input[0]);
            }
        }
        return request;
    }

    public void println(String message) {
        System.out.println(message);
    }

    public void printResponse(Response response) {
        System.out.println("was executing command" + response.getCommandName());
        if (!response.isEmpty()) {
            if (response.isError()) {
                System.out.println("an error occurs when executing the command" + response.getErrorDescription());
            } else {
                if (response.isContainRoutes()) {
                    System.out.println(response.info);
                }
            }
        }
    }
}
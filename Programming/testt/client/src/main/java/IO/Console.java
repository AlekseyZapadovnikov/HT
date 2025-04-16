package IO;

import itemsInArrea.Route;
import java.util.Arrays;
import java.util.Scanner;

public class Console extends ConsoleQuest{
    private final Scanner scanner = new Scanner(System.in);
    private static final String[] ROUTE_COMMANDS = {"add", "update"};

    public Request readCommand() {
        System.out.print("\nВведите команду: ");
        return parseLine(scanner.nextLine());
    }

    public Request parseLine(String line) {
        if (line == null || line.trim().isEmpty()) {
            return new Request("");
        }

        String[] parts = line.trim().split("\\s+");
        String command = parts[0].toLowerCase();
        String[] args = null;
        if (parts.length > 1){
            args = Arrays.copyOfRange(parts, 1, parts.length);
        }

        if (Arrays.asList(ROUTE_COMMANDS).contains(command)) {
            return new Request(command, askRoute());
        }
        if (args != null){
        return new Request(command, args);
        }
        return new Request(command, new String[0]);
    }

    public void printResponse(Response response) {
        if (response == null) {
            System.out.println("Пустой ответ от сервера");
            return;
        }

        System.out.println("\nРезультат выполнения команды:");
        if (response.isError()) {
            System.out.println("[ОШИБКА] " + response.getException());
        } else if (response.isContainRoutes()) {
            response.getRoutes().stream()
                    .forEach(route -> System.out.println(route.toString()));
        } else if (response.getInfo() != null) {
            response.getInfo().forEach(System.out::println);
        } else {
            System.out.println(response.getMessage());
        }
    }

    public void println(String s){
        System.out.println(s);
    }
}
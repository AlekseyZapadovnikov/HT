package IO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Ввод-вывод в консоль
 */
public class Console extends ConsoleQuest {
    private Scanner scanner;
    private static final String[] ROUTE_COMMANDS = {"add", "update"};
    private boolean readFile = false;


    public Console() {
        scanner = new Scanner(System.in);
    }

    public Request readCommand() {
        if (!readFile) {
            System.out.print("\nВведите команду: ");
            return parseLine(scanner.nextLine());
        } else {
            if (scanner.hasNextLine()){
                return parseLine(scanner.nextLine());
            } else {
                return new Request("end");
            }
        }

    }

    public String nextLine(){
        return scanner.nextLine();
    }

    public static Request parseLine(String line) {
        if (line == null || line.trim().isEmpty()) {
            return new Request("");
        }


        String[] parts = line.trim().split("\\s+");
        String command = parts[0].toLowerCase();

        String[] args = null;
        if (parts.length > 1) {
            args = Arrays.copyOfRange(parts, 1, parts.length);
        }

        if (Arrays.asList(ROUTE_COMMANDS).contains(command)) {
            return new Request(command, askRoute());
        }
        if (args != null) {
            return new Request(command, args);
        }
        return new Request(command, new String[0]);
    }

    public String parseCommand(String line){
        String[] parts = line.trim().split("\\s+");
        String command = parts[0].toLowerCase();
        return command;
    }
    public void printResponse(Response response) {
        if (response == null) {
            System.out.println("Пустой ответ от сервера");
            return;
        }
        if (response.isSimpleMessage()) {
            System.out.println(response.getMessage());
        } else {
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
    }

    public void closeFile() {
        if (readFile) {
            scanner.close();
        }
    }

    public void println(String s) {
        System.out.println(s);
    }

    /**
     * Вывод без переноса
     *
     * @param obj объект для вывода
     */
    public void print(Object obj) {
        System.out.print(obj);
    }

    /**
     * Вывод с переносом
     *
     * @param obj объект для вывода
     */
    public void println(Object obj) {
        System.out.println(obj);
    }

    public void warn(String message) {
        System.err.println(message);
    }

    /**
     * Считывает строку из потока ввода
     * NoSuchElementException если введено ctrl+C
     * NullPointerException если ничего не введено
     *
     * @return считанная строка
     */
    public String input() throws IOException {
        print("> ");
        try {
            String inp = scanner.nextLine();
            return inp == null || inp.isEmpty() ? null : inp.trim();
        } catch (NoSuchElementException e) {
            throw new IOException();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Long parseLong(String line) {
        if (line == null) {
            return null;
        }
        return Long.parseLong(line);
    }

    public Double parseDouble(String line) {
        if (line == null) {
            return null;
        }
        return Double.parseDouble(line);
    }

    public Float parseFloat(String line) {
        if (line == null) {
            return null;
        }
        return Float.parseFloat(line);
    }

    public Integer parseInteger(String line) {
        if (line == null) {
            return null;
        }
        return Integer.parseInt(line);
    }
}

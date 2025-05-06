package IO;

import java.io.IOException;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Класс для обработки консольного ввода-вывода.
 * Наследует функциональность класса ConsoleQuest и добавляет
 * методы для чтения команд, их обработки и вывода результатов.
 */
public class Console extends ConsoleQuest {
    private final Scanner scanner;
    private static final String[] ROUTE_COMMANDS = {"add", "update"};
    private boolean readFile = false;

    /**
     * Конструктор по умолчанию. Инициализирует Scanner для чтения из System.in.
     */
    public Console() {
        scanner = new Scanner(System.in);
    }

    /**
     * Читает команду из ввода (консоли или файла).
     *
     * @return объект Request, содержащий команду и аргументы
     */
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

    /**
     * Читает следующую строку из ввода.
     *
     * @return следующая строка ввода
     */
    public String nextLine(){
        return scanner.nextLine();
    }

    /**
     * Парсит строку ввода в объект Request.
     *
     * @param line строка для парсинга
     * @return объект Request с командой и аргументами
     */
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

    /**
     * Извлекает команду из строки ввода.
     *
     * @param line строка ввода
     * @return извлеченная команда
     */
    public String parseCommand(String line){
        String[] parts = line.trim().split("\\s+");
        String command = parts[0].toLowerCase();
        return command;
    }

    /**
     * Выводит ответ от сервера в консоль.
     *
     * @param response объект Response для вывода
     */
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

    /**
     * Закрывает файл, если чтение производилось из файла.
     */
    public void closeFile() {
        if (readFile) {
            scanner.close();
        }
    }

    /**
     * Выводит строку с переносом.
     *
     * @param s строка для вывода
     */
    public void println(String s) {
        System.out.println(s);
    }

    /**
     * Выводит объект без переноса строки.
     *
     * @param obj объект для вывода
     */
    public void print(Object obj) {
        System.out.print(obj);
    }

    /**
     * Выводит объект с переносом строки.
     *
     * @param obj объект для вывода
     */
    public void println(Object obj) {
        System.out.println(obj);
    }

    /**
     * Выводит предупреждающее сообщение в stderr.
     *
     * @param message сообщение для вывода
     */
    public void warn(String message) {
        System.err.println(message);
    }

    /**
     * Считывает строку из потока ввода с приглашением "> ".
     *
     * @return считанная строка (null если ввод пустой)
     * @throws IOException если введено Ctrl+C (NoSuchElementException)
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

    /**
     * Парсит строку в Long.
     *
     * @param line строка для парсинга
     * @return Long значение или null если строка null
     */
    public Long parseLong(String line) {
        if (line == null) {
            return null;
        }
        return Long.parseLong(line);
    }

    /**
     * Парсит строку в Double.
     *
     * @param line строка для парсинга
     * @return Double значение или null если строка null
     */
    public Double parseDouble(String line) {
        if (line == null) {
            return null;
        }
        return Double.parseDouble(line);
    }

    /**
     * Парсит строку в Float.
     *
     * @param line строка для парсинга
     * @return Float значение или null если строка null
     */
    public Float parseFloat(String line) {
        if (line == null) {
            return null;
        }
        return Float.parseFloat(line);
    }

    /**
     * Парсит строку в Integer.
     *
     * @param line строка для парсинга
     * @return Integer значение или null если строка null
     */
    public Integer parseInteger(String line) {
        if (line == null) {
            return null;
        }
        return Integer.parseInt(line);
    }

    public String[] inputCheckIn(){
        System.out.println("Здравствуйте, Вы уже зарегистрированы в системе?");
        System.out.print("введите да/нет: ");
        String ans;
        while (true) {
            ans = scanner.nextLine().toLowerCase();
            if (ans.equals("да") || ans.equals("нет")){
                break;
            } else {
                System.out.print("Ответ должен быть 'да' или 'нет'!!");
                ans = "";
            }
        }

        String login;
        while (true){
            System.out.print("введите логин: ");
            login = scanner.nextLine();
            if (!login.isEmpty()){
                break;
            } else {
                System.out.print("логин не может быть пустым!");
            }
        }

        String password;
        while (true){
            System.out.print("введите пароль: ");
            password = scanner.nextLine();
            if (!password.isEmpty()){
                break;
            } else {
                System.out.print("пароль не может быть пустым!");
            }
        }
        String[] res = new String[3];
        res[0] = login;
        res[1] = password;
        res[2] = ans;

        return res;
    }
}
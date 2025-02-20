package IO;

import itemsInArrea.Coordinates;
import itemsInArrea.Location;
import itemsInArrea.Route;
import managers.ColectionManager;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class ConsoleQuest {
    public static class AskBreak extends Exception {
    }

    // Можно завести один общий Scanner на всё приложение, или
    // создавать новый в каждом методе — на ваше усмотрение.
    private static final Scanner scanner = new Scanner(System.in);

    public static Route askRoute(ColectionManager colectionManager, long id) throws AskBreak {
        try {
            System.out.println("pls, enter information about new Route");

            // Читаем name
            String name;
            while (true) {
                System.out.print("name: ");
                String line = scanner.nextLine().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    name = line;
                    break;
                }
            }

            // Читаем coordinates
            Coordinates coordinates = askCoordinates();

            System.out.print("Enter 'to' Location: ");
            Location locationTo = askLocation();

            // Читаем distance
            long distance;
            while (true) {
                System.out.print("distance please: ");
                String line = scanner.nextLine().trim();
                if (line.equals("exit")) throw new AskBreak();
                try {
                    distance = Long.parseLong(line);
                    break;
                } catch (NumberFormatException e) {
                    System.err.println("Invalid number format, try again.");
                }
            }

            // Читаем locationFrom
            Location locationFrom = askLocation();

            // Если id == -1, значит нужно сгенерировать свободный id
            if (id == -1){
                id = colectionManager.getFreeId();
            }

            return new Route(id, name, coordinates, locationTo, distance, locationFrom);

        } catch (NoSuchElementException | IllegalStateException e) {
            System.err.println("Reading error");
            return null;
        }
    }

    public static Location askLocation() throws AskBreak {
        try {
            // Читаем X
            Float x;
            while (true) {
                System.out.print("coordinates.x: ");
                String line = scanner.nextLine().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try {
                        x = Float.parseFloat(line);
                        break;
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid number format, try again.");
                    }
                }
            }

            // Читаем Y
            long y;
            while (true) {
                System.out.print("coordinates.y: ");
                String line = scanner.nextLine().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try {
                        y = Long.parseLong(line);
                        break;
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid number format, try again.");
                    }
                }
            }

            // Читаем name
            String name;
            while (true) {
                System.out.print("name: ");
                String line = scanner.nextLine().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    name = line;
                    break;
                }
            }

            return new Location(x, y, name);

        } catch (NoSuchElementException | IllegalStateException e) {
            System.err.println("Reading error");
            return null;
        }
    }

    public static Coordinates askCoordinates() throws AskBreak {
        try {
            // Читаем x
            Float x;
            while (true) {
                System.out.print("coordinates.x: ");
                String line = scanner.nextLine().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try {
                        x = Float.parseFloat(line);
                        break;
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid number format, try again.");
                    }
                }
            }

            // Читаем y
            float y;
            while (true) {
                System.out.print("coordinates.y: ");
                String line = scanner.nextLine().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try {
                        y = Float.parseFloat(line);
                        break;
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid number format, try again.");
                    }
                }
            }

            return new Coordinates(x, y);

        } catch (NoSuchElementException | IllegalStateException e) {
            System.err.println("Reading error");
            return null;
        }
    }
}
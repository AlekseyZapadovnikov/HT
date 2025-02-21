package IO;

import comands.Exit;
import itemsInArrea.Coordinates;
import itemsInArrea.Location;
import itemsInArrea.Route;
import managers.ColectionManager;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class ConsoleQuest {
    public static Exit exit = new Exit("exit", "виход");

    private static final Scanner scanner = new Scanner(System.in);

    public static Route askRoute(ColectionManager colectionManager, long id) {
        try {
            System.out.println("pls, enter information about new Route");

            // Читаем name
            String name;
            while (true) {
                System.out.print("name: ");
                String line = scanner.nextLine().trim();
                if (line.equals("exit")) exit.execute();
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
                if (line.equals("exit")) exit.execute();
                try {
                    distance = Long.parseLong(line);
                    break;
                } catch (NumberFormatException e) {
                    System.err.println("Invalid number format, try again.");
                }
            }

            // Читаем locationFrom
            System.out.println("enter info about 'from' Location");
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

    public static Location askLocation() {
        try {
            // Читаем X
            Float x;
            while (true) {
                System.out.print("coordinates.x: ");
                String line = scanner.nextLine().trim();
                if (line.equals("exit")) exit.execute();
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
                if (line.equals("exit")) exit.execute();
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
                if (line.equals("exit")) exit.execute();
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

    public static Coordinates askCoordinates() {
        try {
            // Читаем x
            System.out.println("enter info about coordination");
            Float x;
            while (true) {
                System.out.print("coordinates.x: ");
                String line = scanner.nextLine().trim();
                if (line.equals("exit")) exit.execute();
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
                if (line.equals("exit")) exit.execute();
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
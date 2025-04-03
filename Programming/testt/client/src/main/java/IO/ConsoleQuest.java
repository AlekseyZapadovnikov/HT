package IO;


import itemsInArrea.Coordinates;
import itemsInArrea.Location;
import itemsInArrea.Route;

import java.util.NoSuchElementException;
import java.util.Scanner;


public class ConsoleQuest {


    static final Scanner scanner = new Scanner(System.in);


    public static Route askRoute() {
        try {
            System.out.println("pls, enter information about new Route");

            // Read name
            String name;
            while (true) {
                System.out.print("name: ");
                String line = scanner.nextLine().trim();
                if (line.equals("exit")) System.exit(0);
                if (!line.isEmpty()) {
                    name = line;
                    break;
                }
            }

            // Read coordinates
            Coordinates coordinates = askCoordinates();

            System.out.print("Enter 'to' Location: ");
            Location locationTo = askLocation();

            // Read distance (must be >= 1)
            long distance;
            while (true) {
                System.out.print("distance please: ");
                String line = scanner.nextLine().trim();
                if (line.equals("exit")) System.exit(0);
                try {
                    distance = Long.parseLong(line);
                    if (distance < 1) {
                        System.err.println("distance must be > 1, try again");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Invalid number format, try again.");
                }
            }

            // Read locationFrom
            System.out.println("enter info about 'from' Location");
            Location locationFrom = askLocation();


            return new Route(name, coordinates, locationTo, distance, locationFrom);

        } catch (NoSuchElementException | IllegalStateException e) {
            System.err.println("Reading error");
            return null;
        }
    }


    public static Location askLocation() {
        try {
            // Read X (float)
            Float x;
            while (true) {
                System.out.print("coordinates.x: ");
                String line = scanner.nextLine().trim();
                if (line.equals("exit")) System.exit(0);
                if (!line.isEmpty()) {
                    try {
                        x = Float.parseFloat(line);
                        break;
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid number format, try again.");
                    }
                }
            }

            long y;
            while (true) {
                System.out.print("coordinates.y: ");
                String line = scanner.nextLine().trim();
                if (line.equals("exit")) System.exit(0);
                if (!line.isEmpty()) {
                    try {
                        y = Long.parseLong(line);
                        break;
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid number format, try again.");
                    }
                }
            }


            String name;
            while (true) {
                System.out.print("name: ");
                String line = scanner.nextLine().trim();
                if (line.equals("exit")) System.exit(0);
                if (!line.isEmpty()) {
                    name = line;
                    break;
                } else {
                    name = null;
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
            System.out.println("enter info about coordination");

            // Read x
            Float x;
            while (true) {
                System.out.print("coordinates.x: ");
                String line = scanner.nextLine().trim();
                if (line.equals("exit")) System.exit(0);
                if (!line.isEmpty()) {
                    try {
                        x = Float.parseFloat(line);
                        break;
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid number format, try again.");
                    }
                }
            }

            // Read y
            float y;
            while (true) {
                System.out.print("coordinates.y: ");
                String line = scanner.nextLine().trim();
                if (line.equals("exit")) System.exit(0);
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

    public static Scanner getScanner(){
        return scanner;
    }
}
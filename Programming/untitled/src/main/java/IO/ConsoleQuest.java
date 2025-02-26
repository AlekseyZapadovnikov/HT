package IO;

import comands.Exit;
import itemsInArrea.Coordinates;
import itemsInArrea.Location;
import itemsInArrea.Route;
import managers.ColectionManager;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Provides console-based prompts (questions) to create and populate
 * {@link Route}, {@link Coordinates}, and {@link Location} objects.
 * <p>
 * Uses the standard input {@link Scanner} to read user input and
 * validate it before constructing domain objects.
 * </p>
 */
public class ConsoleQuest {

    /**
     * A command that, when invoked, terminates the application ("exit").
     * Used to intercept "exit" user input.
     */
    public static Exit exit = new Exit("exit", "виход");

    /**
     * A {@link Scanner} to read from the standard input stream.
     */
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Prompts the user to enter data for creating a new or updated {@link Route} object.
     * <ul>
     *   <li>Requests the route's name.</li>
     *   <li>Coordinates for the route via {@link #askCoordinates()}.</li>
     *   <li>A "to" {@link Location}.</li>
     *   <li>A distance value (must be &gt;= 1).</li>
     *   <li>A "from" {@link Location}.</li>
     * </ul>
     * If {@code id == -1}, the method obtains a free ID from {@link ColectionManager#getFreeId()}.
     * <p>
     * If the user types "exit" at any prompt, {@link Exit#execute()} is called.
     * </p>
     *
     * @param colectionManager the collection manager used to get a free ID if needed
     * @param id               an existing route ID or -1 if a new ID is required
     * @return a newly constructed {@code Route} or {@code null} if an exception occurs
     */
    public static Route askRoute(ColectionManager colectionManager, long id) {
        try {
            System.out.println("pls, enter information about new Route");

            // Read name
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

            // Read coordinates
            Coordinates coordinates = askCoordinates();

            System.out.print("Enter 'to' Location: ");
            Location locationTo = askLocation();

            // Read distance (must be >= 1)
            long distance;
            while (true) {
                System.out.print("distance please: ");
                String line = scanner.nextLine().trim();
                if (line.equals("exit")) exit.execute();
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

            // If id == -1, we need a free ID from colectionManager
            if (id == -1) {
                id = colectionManager.getFreeId();
            }

            return new Route(id, name, coordinates, locationTo, distance, locationFrom);

        } catch (NoSuchElementException | IllegalStateException e) {
            System.err.println("Reading error");
            return null;
        }
    }

    /**
     * Prompts the user to enter information for creating a {@link Location} object.
     * <ul>
     *   <li>X coordinate (float)</li>
     *   <li>Y coordinate (long)</li>
     *   <li>Name (non-empty string)</li>
     * </ul>
     * If the user types "exit" at any prompt, {@link Exit#execute()} is called.
     *
     * @return a constructed {@code Location} or {@code null} if an exception occurs
     */
    public static Location askLocation() {
        try {
            // Read X (float)
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

            // Read Y (long)
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

            // Read name
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

    /**
     * Prompts the user to enter information for creating {@link Coordinates}.
     * <ul>
     *   <li>X coordinate (float)</li>
     *   <li>Y coordinate (float)</li>
     * </ul>
     * If the user types "exit", {@link Exit#execute()} is invoked.
     *
     * @return a new {@code Coordinates} or {@code null} on failure
     */
    public static Coordinates askCoordinates() {
        try {
            System.out.println("enter info about coordination");

            // Read x
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

            // Read y
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
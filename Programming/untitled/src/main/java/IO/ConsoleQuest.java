package IO;

import itemsInArrea.Coordinates;
import itemsInArrea.Location;
import itemsInArrea.Route;

import java.util.NoSuchElementException;

public class ConsoleQuest {
    public static class AskBreak extends Exception {
    }

    public static Route askRoute(Console console, int id) throws AskBreak {
        try {
            String name;
            while (true) {
                console.print("name: ");
                name = console.readln().trim();
                if (name.equals("exit")) throw new AskBreak();
                if (!name.isEmpty()) break;
            }
            var coordinates = askCoordinates(console);
            console.print("Enter 'to' Location");
            var locationTo = askLocation(console);
            long distance;
            while (true) {
                console.print("distance please: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                try {
                    distance = Long.parseLong(line);
                    break;
                } catch (NumberFormatException e) {
                    console.printError("Invalid number format, try again.");
                }
            }
            var locationFrom = askLocation(console);
            return new Route(id, name, coordinates, locationTo, distance, locationFrom);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Reading error");
            return null;
        }
    }

    public static Location askLocation(Console console) throws AskBreak {
        try {
            Float x;
            while (true) {
                console.print("coordinates.x: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try {
                        x = Float.parseFloat(line);
                        break;
                    } catch (NumberFormatException e) {
                        console.printError("Invalid number format, try again.");
                    }
                }
            }
            long y;
            while (true) {
                console.print("coordinates.y: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.equals("")) {
                    try {
                        y = Long.parseLong(line);
                        break;
                    } catch (NumberFormatException e) {
                        console.printError("Invalid number format, try again.");
                    }
                }
            }
            String name;
            while (true) {
                console.print("name: ");
                name = console.readln().trim();
                if (name.equals("exit")) throw new AskBreak();
                if (!name.isEmpty()) break;
            }
            return new Location(x, y, name);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Reading error");
            return null;
        }
    }

    public static Coordinates askCoordinates(Console console) throws AskBreak {
        try {
            Float x;
            while (true) {
                console.print("coordinates.x: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try {
                        x = Float.parseFloat(line);
                        break;
                    } catch (NumberFormatException e) {
                        console.printError("Invalid number format, try again.");
                    }
                }
            }
            float y;
            while (true) {
                console.print("coordinates.y: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.equals("")) {
                    try {
                        y = Float.parseFloat(line);
                        break;
                    } catch (NumberFormatException e) {
                        console.printError("Invalid number format, try again.");
                    }
                }
            }
            return new Coordinates(x, y);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Reading error");
            return null;
        }
    }
}
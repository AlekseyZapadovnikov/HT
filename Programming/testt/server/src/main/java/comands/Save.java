package comands;

import IO.RouteXMLWriter;
import managers.ColectionManager;

import java.io.IOException;

/**
 * Command to save data to an XML file.
 * <p>
 * This command informs the user that the data will be saved to "Saves.xml". It uses a {@link RouteXMLWriter}
 * to write the routes obtained from the {@link ColectionManager} to an XML file. If an {@link IOException} occurs
 * during the file writing process, a {@link RuntimeException} is thrown.
 * </p>
 */
public class Save extends Command {
    private final RouteXMLWriter saver;
    private final ColectionManager colectionManager;

    /**
     * Constructs a new Save command.
     *
     * @param name             the name of the command.
     * @param description      a short description of the command.
     * @param saver            the {@link RouteXMLWriter} used to write the routes.
     * @param colectionManager the {@link ColectionManager} that holds the routes to save.
     */
    public Save(String name, String description, RouteXMLWriter saver, ColectionManager colectionManager) {
        super(name, description);
        this.saver = saver;
        this.colectionManager = colectionManager;
    }

    /**
     * Executes the save command.
     * <p>
     * This method prints a message stating that the data will be saved to "Saves.xml". It then attempts to write the routes
     * from the collection manager to the XML file using the provided {@link RouteXMLWriter}. Upon successful write,
     * a confirmation message is printed. If there is a problem with writing to the file, an error message is printed
     * and a {@link RuntimeException} is thrown.
     * </p>
     *
     * @param args command line arguments (not used in this command).
     */
    @Override
    public void execute(String[] args) {
        System.out.println("Data will be saved in Saves.xml");
        try {
            saver.writeRoutes(colectionManager.getroutes());
            System.out.println("Data were saved successfully");
        } catch (IOException e) {
            System.out.println("Having problems writing to a file");
            throw new RuntimeException(e);
        }
    }
}
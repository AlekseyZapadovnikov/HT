package managers;

import IO.RouteXMLScaner;
import IO.RouteXMLWriter;
import comands.Command;
import comands.*;

import java.io.File;
import java.io.IOException;

/**
 * Initializes and manages the application's main components:
 * <ul>
 *   <li>{@link ColectionManager} for storing and manipulating routes.</li>
 *   <li>{@link CommandManager} for registering and retrieving command objects.</li>
 *   <li>{@link RouteXMLScaner} and {@link RouteXMLWriter} for reading/writing route data.</li>
 * </ul>
 * <p>
 * The {@code Runner} class also contains the logic to fill the {@code CommandManager}
 * with all the available commands.
 * </p>
 */
public class Runner {

    private ColectionManager colectionManager;
    private CommandManager commandManager;
    private RouteXMLScaner xmlScaner;
    private RouteXMLWriter xmlWriter;

    /**
     * Constructs a {@code Runner}, initializing the XML scanner and writer,
     * creating a {@link ColectionManager}, and instantiating a {@link CommandManager}.
     * <p>
     * The file <em>"prog_lab_5_data.xml"</em> is used as the data source,
     * and <em>"test.xml"</em> is used as the output file for saving data.
     * </p>
     *
     * @throws RuntimeException if the required file is not found
     */
    public Runner() {
        try {
            this.xmlScaner = new RouteXMLScaner(new File("src/main/resources/prog_lab_5_data.xml"));
            this.xmlWriter = new RouteXMLWriter("src/main/resources/test.xml");
            colectionManager = new ColectionManager(this.xmlScaner, this.xmlWriter);
            commandManager = new CommandManager();
        } catch (IOException e) {
            System.out.println("file not found");
            throw new RuntimeException(e);
        }
    }

    /**
     * Executes the main run sequence:
     * <ul>
     *   <li>Populates the {@link CommandManager} with all available commands.</li>
     *   <li>Updates the collection manager (e.g., sorts the routes).</li>
     * </ul>
     */
    public void run() {
        fillCommands();
        colectionManager.update();
    }

    /**
     * Registers all commands in the {@link CommandManager},
     * associating each command's name with its respective {@link Command} instance.
     */
    public void fillCommands() {
        // help
        Command help = new Help(
                "help",
                "вывести справку по доступным командам",
                this.commandManager
        );
        this.commandManager.addCommand(help);

        // info
        Command info = new Info(
                "info",
                "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)",
                this.colectionManager
        );
        this.commandManager.addCommand(info);

        // show
        Command show = new Show(
                "show",
                "вывести в стандартный поток вывода все элементы коллекции в строковом представлении",
                this.colectionManager
        );
        this.commandManager.addCommand(show);

        // add {element}
        Command add = new Add(
                "add",
                "добавить новый элемент в коллекцию",
                this.colectionManager
        );
        this.commandManager.addCommand(add);

        // update id {element}
        Command update = new Update(
                "update",
                "обновить значение элемента коллекции, id которого равен заданному",
                this.colectionManager
        );
        this.commandManager.addCommand(update);

        // remove_by_id id
        Command removeById = new RemoveById(
                "remove_by_id",
                "удалить элемент из коллекции по его id",
                this.colectionManager
        );
        this.commandManager.addCommand(removeById);

        // clear
        Command clear = new Clear(
                "clear",
                "очистить коллекцию",
                this.colectionManager
        );
        this.commandManager.addCommand(clear);

        // save
        Command save = new Save(
                "save",
                "сохранить коллекцию в файл",
                this.xmlWriter,
                this.colectionManager
        );
        this.commandManager.addCommand(save);

        // execute_script file_name
        Command executeScript = new ExecuteScript(
                "execute_script",
                "считать и исполнить скрипт из указанного файла",
                this.commandManager
        );
        this.commandManager.addCommand(executeScript);

        // exit
        Command exit = new Exit(
                "exit",
                "завершить программу (без сохранения в файл)"
        );
        this.commandManager.addCommand(exit);

        // head
        Command head = new Head(
                "head",
                "вывести первый элемент коллекции",
                this.colectionManager
        );
        this.commandManager.addCommand(head);

        // remove_head
        Command removeHead = new RemoveHead(
                "remove_head",
                "вывести первый элемент коллекции и удалить его",
                this.colectionManager
        );
        this.commandManager.addCommand(removeHead);

        // remove_lower {element}
        Command removeLower = new RemoveLower(
                "remove_lower",
                "удалить из коллекции все элементы, меньшие, чем заданный",
                this.colectionManager
        );
        this.commandManager.addCommand(removeLower);

        // average_of_distance
        Command averageOfDistance = new AveregeOfDistance(
                "average_of_distance",
                "вывести среднее значение поля distance для всех элементов коллекции",
                this.colectionManager
        );
        this.commandManager.addCommand(averageOfDistance);

        // count_by_distance distance
        Command countByDistance = new CountByDistance(
                "count_by_distance",
                "вывести количество элементов, значение поля distance которых равно заданному",
                this.colectionManager
        );
        this.commandManager.addCommand(countByDistance);

        // print_ascending
        Command printAscending = new PrintAscending(
                "print_ascending",
                "вывести элементы коллекции в порядке возрастания",
                this.colectionManager
        );
        this.commandManager.addCommand(printAscending);
    }

    /**
     * Gets the underlying {@link ColectionManager}, which handles the stored routes.
     *
     * @return the current {@link ColectionManager} instance
     */
    public ColectionManager getColectionManager() {
        return colectionManager;
    }

    /**
     * Gets the {@link RouteXMLWriter} associated with this runner.
     *
     * @return the {@link RouteXMLWriter} for saving routes to file
     */
    public RouteXMLWriter getXmlWriter() {
        return xmlWriter;
    }

    /**
     * Gets the {@link RouteXMLScaner} used to read initial route data.
     *
     * @return the {@link RouteXMLScaner} instance
     */
    public RouteXMLScaner getXmlScaner() {
        return xmlScaner;
    }

    /**
     * Returns the {@link CommandManager}, containing all registered commands.
     *
     * @return the current {@link CommandManager}
     */
    public CommandManager getCommandManager() {
        return commandManager;
    }
}
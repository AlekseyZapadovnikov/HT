//package comands;
//
///**
// * The Exit command class extends <code>Command</code> and terminates the program upon execution.
// */
//public class Exit extends Command {
//
//    /**
//     * Constructs an Exit command with the given name and description.
//     *
//     * @param name        the name of the command
//     * @param description a brief description of what this command does
//     */
//    public Exit(String name, String description) {
//        super(name, description);
//    }
//
//    /**
//     * Overrides the execute method with arguments to exit the application.
//     *
//     * @param args array of string arguments (not used by this command)
//     */
//    @Override
//    public void execute(String[] args) {
//        System.exit(0);
//    }
//
//    /**
//     * An overloaded execute method without arguments that also exits the application.
//     */
//    public void execute() {
//        System.exit(0);
//    }
//}
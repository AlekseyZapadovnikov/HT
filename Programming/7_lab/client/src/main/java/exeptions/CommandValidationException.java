package exeptions;

public class CommandValidationException extends Exception {
    public CommandValidationException(String message) {
        super(message);
    }
}
package exeptions;

/**
 * Исключение валидации данных
 */
public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}

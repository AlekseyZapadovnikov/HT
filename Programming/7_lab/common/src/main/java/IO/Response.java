package IO;

import itemsInArrea.Route;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Класс для передачи ответов от сервера клиенту.
 * Содержит результаты выполнения команд, информацию об ошибках
 * и коллекции объектов маршрутов (Route).
 * Реализует интерфейс {@link Serializable} для сериализации при передаче по сети.
 */
public class Response implements Serializable {
    private boolean isError = false;
    private Exception exception;
    private String errorDescription;
    private LinkedList<Route> routes;
    private Route route;
    private boolean isContainRoutes;
    private String commandName;
    private boolean isEmpty = false;
    private String message = "";
    ArrayList<String> info;
    private boolean isSimpleMessage = false;

    /**
     * Создает ответ об ошибке.
     *
     * @param e исключение, вызвавшее ошибку
     * @param description описание ошибки
     */
    public Response(Exception e, String description) {
        isError = true;
        exception = e;
        errorDescription = description;
    }

    /**
     * Создает ответ с коллекцией маршрутов.
     *
     * @param commandName имя выполненной команды
     * @param routes коллекция маршрутов ({@link LinkedList} of {@link Route})
     */
    public Response(String commandName, LinkedList<Route> routes) {
        this.routes = routes;
        this.commandName = commandName;
        isContainRoutes = true;
    }

    /**
     * Создает ответ с информационными сообщениями.
     *
     * @param commandName имя выполненной команды
     * @param info список информационных сообщений ({@link ArrayList} of String)
     */
    public Response(String commandName, ArrayList<String> info) {
        this.info = info;
        this.commandName = commandName;
    }

    /**
     * Создает ответ с одним маршрутом.
     *
     * @param commandName имя выполненной команды
     * @param route объект маршрута ({@link Route})
     */
    public Response(String commandName, Route route) {
        this.route = route;
        routes = new LinkedList<Route>();
        this.routes.add(route);
        isContainRoutes = true;
    }

    /**
     * Создает ответ с текстовым сообщением.
     *
     * @param commandName имя выполненной команды
     * @param message текстовое сообщение
     */
    public Response(String commandName, String message) {
        this.commandName = commandName;
        this.message = message;
    }

    /**
     * Создает пустой ответ.
     */
    public Response() {
        isEmpty = true;
    }

    /**
     * Создает простой текстовый ответ (без привязки к команде).
     *
     * @param message текстовое сообщение
     */
    public Response(String message) {
        this.message = message;
        isSimpleMessage = true;
    }

    /**
     * Формирует строковое представление ответа.
     *
     * @return строковое описание ответа, включающее:
     *         - имя команды
     *         - список маршрутов (если есть)
     *         - сообщение об ошибке (если есть)
     *         - статус выполнения
     */
    @Override
    public String toString() {
        String s = "was executing command " + commandName + "\n";
        if (isContainRoutes) {
            for (Route route : routes) {
                s += route.toString() + "\n";
            }
        } else {
            if (!isEmpty) {
                if (isError) {
                    s += "an error occurs when executing the command \n" + errorDescription + "\n";
                } else {
                    s += message;
                    s += "\ncommand completed successfully";
                }
            }
        }
        return s;
    }

    /**
     * Проверяет, содержит ли ответ информацию об ошибке.
     *
     * @return true если ответ содержит ошибку, false в противном случае
     */
    public boolean isError() {
        return isError;
    }

    /**
     * Возвращает исключение, связанное с ошибкой.
     *
     * @return объект {@link Exception} или null если ошибки нет
     */
    public Exception getException() {
        return exception;
    }

    /**
     * Возвращает сообщение исключения.
     *
     * @return сообщение об ошибке или null если ошибки нет
     */
    public String getExceptionMessage() {
        return exception.getMessage();
    }

    /**
     * Возвращает описание ошибки.
     *
     * @return описание ошибки или null если ошибки нет
     */
    public String getErrorDescription() {
        return errorDescription;
    }

    /**
     * Возвращает список маршрутов.
     *
     * @return список маршрутов ({@link List} of {@link Route}) или null если маршрутов нет
     */
    public List<Route> getRoutes() {
        return routes;
    }

    /**
     * Возвращает одиночный маршрут.
     *
     * @return объект {@link Route} или null если маршрута нет
     */
    public Route getRoute() {
        return route;
    }

    /**
     * Проверяет, содержит ли ответ маршруты.
     *
     * @return true если ответ содержит маршруты, false в противном случае
     */
    public boolean isContainRoutes() {
        return isContainRoutes;
    }

    /**
     * Возвращает имя команды.
     *
     * @return имя команды или null если не установлено
     */
    public String getCommandName() {
        return commandName;
    }

    /**
     * Проверяет, является ли ответ пустым.
     *
     * @return true если ответ не содержит данных, false в противном случае
     */
    public boolean isEmpty() {
        return isEmpty;
    }

    /**
     * Возвращает текстовое сообщение ответа.
     *
     * @return текстовое сообщение или пустая строка если не установлено
     */
    public String getMessage() {
        return message;
    }

    /**
     * Возвращает список информационных сообщений.
     *
     * @return список сообщений ({@link List} of String) или null если отсутствует
     */
    public List<String> getInfo() {
        return info;
    }

    /**
     * Проверяет, является ли ответ простым текстовым сообщением.
     *
     * @return true если ответ содержит только сообщение без привязки к команде
     */
    public boolean isSimpleMessage() {
        return isSimpleMessage;
    }
}
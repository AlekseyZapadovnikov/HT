package IO;

import itemsInArrea.Route;
import java.io.Serializable;

/**
 * Класс для передачи запросов между клиентом и сервером.
 * Содержит команду, аргументы команды и связанный маршрут (Route).
 * Реализует интерфейс {@link Serializable} для сериализации при передаче по сети.
 */
public class Request implements Serializable {
    private String command;
    private String[] args;
    private Route route;
    private boolean containRoute = false;
    private boolean argsContain = false;
    private boolean isError = false;
    private Exception exception;
    private String userLogin;
    private static final long serialVersionUID = 1L;

    /**
     * Создает запрос с командой и аргументами.
     *
     * @param command команда для выполнения
     * @param args массив аргументов команды
     */
    public Request(String command, String[] args) {
        this.command = command;
        this.args = args;
        this.argsContain = args != null && args.length > 0;
    }

    /**
     * Создает запрос с командой и связанным маршрутом.
     *
     * @param command команда для выполнения
     * @param route объект маршрута ({@link Route})
     */
    public Request(String command, Route route) {
        this.command = command;
        this.route = route;
        this.containRoute = route != null;
        this.argsContain = false;
    }

    /**
     * Создает запрос только с командой.
     *
     * @param command команда для выполнения
     */
    public Request(String command) {
        this.command = command;
    }

    /**
     * Создает запрос об ошибке.
     *
     * @param e исключение, содержащее информацию об ошибке
     */
    public Request(Exception e) {
        isError = true;
        this.exception = e;
    }

    public Request(String[] args){
        this.args = args;
    }

    /**
     * Возвращает команду запроса.
     *
     * @return строка с командой
     */
    public String getCommand() {
        return command;
    }

    /**
     * Возвращает аргументы команды.
     *
     * @return массив аргументов команды
     */
    public String[] getArgs() {
        return args;
    }

    /**
     * Проверяет, содержит ли запрос маршрут.
     *
     * @return true если запрос содержит маршрут, false в противном случае
     */
    public boolean isContainRoute() {
        return containRoute;
    }

    /**
     * Проверяет, является ли запрос сообщением об ошибке.
     *
     * @return true если запрос содержит ошибку, false в противном случае
     */
    public boolean isError() {
        return isError;
    }

    /**
     * Возвращает маршрут, связанный с запросом.
     *
     * @return объект {@link Route} или null если маршрут отсутствует
     */
    public Route getRoute() {
        return route;
    }

    /**
     * Проверяет, содержит ли запрос аргументы.
     *
     * @return true если запрос содержит аргументы, false в противном случае
     */
    public boolean isArgsContain() {
        return argsContain;
    }

    /**
     * Возвращает исключение, связанное с запросом.
     *
     * @return объект {@link Exception} или null если исключение отсутствует
     */
    public Exception getException() {
        return exception;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }
}
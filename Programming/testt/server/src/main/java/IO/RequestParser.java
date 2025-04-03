//package IO;
//
//import itemsInArrea.Route;
//import managers.NetworkMessage;
//
//public class RequestParser {
//
//    /**
//     * Преобразует сериализованный объект NetworkMessage в Request
//     * и извлекает данные из запроса.
//     */
//    public ParsedRequest parse(NetworkMessage message) {
//        if (!(message instanceof Request)) {
//            throw new IllegalArgumentException("Неподдерживаемый тип сообщения: ожидается Request");
//        }
//
//        Request request = (Request) message;
//        String command = request.getCommand();
//        String[] args = request.getArgs();
//        Route route = request.isContainRoute() ? request.getRoute() : null;
//
////        // Дополнительная валидация (пример)
////        validateCommand(command);
////        validateArgs(command, args);
//
//        return new ParsedRequest(command, args, route);
//    }
//
//    // Валидация команды
//    private void validateCommand(String command) {
//        if (command == null || command.trim().isEmpty()) {
//            throw new IllegalArgumentException("Команда не может быть пустой");
//        }
//    }
//
//    // Валидация аргументов (можно добавить логику для конкретных команд)
//    private void validateArgs(String command, String[] args) {
//        if (command.equals("add") && args.length < 2) {
//            throw new IllegalArgumentException("Команда 'add' требует 2 аргумента");
//        }
//    }
//
//    // Вспомогательный класс для хранения распарсенных данных
//    public static class ParsedRequest {
//        private final String command;
//        private final String[] args;
//        private final Route route;
//
//        public ParsedRequest(String command, String[] args, Route route) {
//            this.command = command;
//            this.args = args;
//            this.route = route;
//        }
//
//        // Геттеры
//        public String getCommand() {
//            return command;
//        }
//
//        public String[] getArgs() {
//            return args;
//        }
//
//        public Route getRoute() {
//            return route;
//        }
//    }
//}
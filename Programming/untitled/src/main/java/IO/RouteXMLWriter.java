package IO;

import itemsInArrea.Route;
import itemsInArrea.Coordinates;
import itemsInArrea.Location;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Класс для записи объектов Route в XML-файл.
 */
public class RouteXMLWriter implements AutoCloseable {
    private int tabAmount = 0;     // отвечает за текущий уровень отступов в XML
    private FileWriter fileWriter;

    /**
     * Создаёт (или перезаписывает) указанный XML-файл и записывает корневой тег <routes>.
     * @param fileName имя файла (путь), куда писать XML.
     * @throws IOException если не удаётся открыть/создать файл для записи.
     */
    public RouteXMLWriter(String fileName) throws IOException {
        this.fileWriter = new FileWriter(new File(fileName));
        // Записываем открывающий тег корня
        fileWriter.write("<routes>\n");
        tabAmount++;
    }

    /**
     * Запись одного объекта Route в файл (добавляет <route> ... </route>).
     * @param route объект для записи
     * @throws IOException при ошибках записи в файл
     */
    public void writeRoute(Route route) throws IOException {
        // Открываем <route>
        fileWriter.write(openTag("route") + "\n");
        tabAmount++;

        // <id>
        fileWriter.write(tagWithValue("id", String.valueOf(route.getId())) + "\n");

        // <name>
        fileWriter.write(tagWithValue("name", route.getName()) + "\n");

        // <coordinates>
        fileWriter.write(openTag("coordinates") + "\n");
        tabAmount++;
        fileWriter.write(tagWithValue("x", route.getCoordinates().getX().toString()) + "\n");
        fileWriter.write(tagWithValue("y", String.valueOf(route.getCoordinates().getY())) + "\n");
        tabAmount--;
        fileWriter.write(closeTag("coordinates") + "\n");

        // <from>
        fileWriter.write(openTag("from") + "\n");
        tabAmount++;
        fileWriter.write(tagWithValue("x", route.getFrom().getX().toString()) + "\n");
        fileWriter.write(tagWithValue("y", String.valueOf(route.getFrom().getY())) + "\n");
        fileWriter.write(tagWithValue("name", route.getFrom().getName()) + "\n");
        tabAmount--;
        fileWriter.write(closeTag("from") + "\n");

        // <distance>
        fileWriter.write(tagWithValue("distance", String.valueOf(route.getDistance())) + "\n");

        // <to>
        // (если объект 'to' не null, иначе можно пропустить)
        if (route.getTo() != null) {
            fileWriter.write(openTag("to") + "\n");
            tabAmount++;
            fileWriter.write(tagWithValue("x", route.getTo().getX().toString()) + "\n");
            fileWriter.write(tagWithValue("y", String.valueOf(route.getTo().getY())) + "\n");
            fileWriter.write(tagWithValue("name", route.getTo().getName()) + "\n");
            tabAmount--;
            fileWriter.write(closeTag("to") + "\n");
        }

        // Закрываем </route>
        tabAmount--;
        fileWriter.write(closeTag("route") + "\n");
    }

    /**
     * Записывает в файл все объекты из переданного списка routes.
     * @param routes список маршрутов (Route), которые нужно записать в XML.
     * @throws IOException при ошибках записи в файл.
     */
    public void writeRoutes(LinkedList<Route> routes) throws IOException {
        for (Route route : routes) {
            writeRoute(route);
        }
    }

    /**
     * Завершает запись (закрывает корневой тег </routes>) и освобождает ресурс записи.
     * Вызывать после записи всех объектов.
     */
    @Override
    public void close() throws IOException {
        // Записываем закрывающий тег корня
        fileWriter.write("</routes>\n");
        fileWriter.close();
    }

    /* ======================= Вспомогательные методы ======================= */

    /**
     * Возвращает строку открывающего тега с учётом текущего уровня отступа: <tag>
     */
    private String openTag(String tag) {
        return getIndent() + "<" + tag + ">";
    }

    /**
     * Возвращает строку закрывающего тега с учётом текущего уровня отступа: </tag>
     */
    private String closeTag(String tag) {
        return getIndent() + "</" + tag + ">";
    }

    /**
     * Возвращает строку с открывающим и закрывающим тегом с содержимым: <tag>value</tag>,
     * учитывая текущий уровень отступа.
     */
    private String tagWithValue(String tag, String value) {
        return getIndent() + "<" + tag + ">" + value + "</" + tag + ">";
    }

    /**
     * Генерирует пробелы (или табы) для текущего уровня вложенности.
     */
    private String getIndent() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tabAmount; i++) {
            sb.append("    "); // например, 4 пробела на уровень
        }
        return sb.toString();
    }
}

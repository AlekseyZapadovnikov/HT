package IO;

import itemsInArrea.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;
import org.xml.sax.InputSource;
import java.io.StringReader;

public class RouteXMLParser {

    public static LinkedList<Route> parseRoutes(String xmlFilePath) throws Exception {
        // 1) Читаем файл в строку при помощи Scanner
        String xmlContent = readFileToString(xmlFilePath);

        // 2) Парсим строку как XML (DOM-парсер)
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        // Превращаем строку в Document (используя InputSource + StringReader)
        InputSource is = new InputSource(new StringReader(xmlContent));
        Document doc = builder.parse(is);
        doc.getDocumentElement().normalize();

        // 3) Извлекаем все <route> ... </route>
        NodeList routeNodes = doc.getElementsByTagName("route");

        LinkedList<Route> routes = new LinkedList<>();

        for (int i = 0; i < routeNodes.getLength(); i++) {
            Node routeNode = routeNodes.item(i);
            if (routeNode.getNodeType() == Node.ELEMENT_NODE) {
                Element routeElement = (Element) routeNode;

                // Извлекаем поля

                long id = Long.parseLong(getTagValue("id", routeElement));
                String name = getTagValue("name", routeElement);

                // coordinates
                Element coordsEl = (Element) routeElement.getElementsByTagName("coordinates").item(0);
                Float cx = Float.valueOf(getTagValue("x", coordsEl));
                float cy = Float.parseFloat(getTagValue("y", coordsEl));
                Coordinates coordinates = new Coordinates(cx, cy);

                // creationDate
                LocalDate creationDate = LocalDate.parse(getTagValue("creationDate", routeElement));

                // from (обязательно есть)
                Element fromEl = (Element) routeElement.getElementsByTagName("from").item(0);
                Location from = parseLocation(fromEl);

                // to (опционально)
                NodeList toNL = routeElement.getElementsByTagName("to");
                Location to = null;
                if (toNL.getLength() > 0) {
                    Element toEl = (Element) toNL.item(0);
                    to = parseLocation(toEl);
                }

                long distance = Long.parseLong(getTagValue("distance", routeElement));

                // Создаём объект Route
                Route route = new Route(
                        id,
                        name,
                        coordinates,
                        creationDate,
                        from,
                        to,
                        distance
                );

                // Добавляем в список
                routes.add(route);
            }
        }

        return routes;
    }

    /**
     * Вспомогательный метод, который считывает все строки файла в одну большую строку.
     * Использует java.util.Scanner, как указано в задании.
     */
    private static String readFileToString(String filePath) throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        try (Scanner scanner = new Scanner(new File(filePath))) {

            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine()).append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * Получение текстового содержимого первого элемента <tagName> внутри родительского элемента parent.
     */
    private static String getTagValue(String tagName, Element parent) {
        NodeList nl = parent.getElementsByTagName(tagName);
        if (nl.getLength() == 0) {
            return null; // Или кидайте исключение, если поле обязательно
        }
        return nl.item(0).getTextContent().trim();
    }

    /**
     * Парсит элемент <location> (x, y, name).
     */
    private static Location parseLocation(Element locEl) {
        Float lx = Float.valueOf(getTagValue("x", locEl));
        long ly = Long.parseLong(getTagValue("y", locEl));
        String lname = getTagValue("name", locEl);
        if (lname == null) {
            // Поле может быть null
            lname = null;
        } else {
            lname = lname.trim();
        }
        return new Location(lx, ly, lname);
    }
}
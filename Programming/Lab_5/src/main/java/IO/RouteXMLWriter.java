package IO;

import itemsInArrea.Route;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

/**
 * The RouteXMLWriter class writes Route objects to an XML file.
 * <p>
 * This class creates (or overwrites) an XML file and writes the root tag &lt;routes&gt;. It provides methods
 * to write individual Route objects as XML elements and to write a list of Route objects.
 * </p>
 */
public class RouteXMLWriter implements AutoCloseable {
    private int tabAmount = 0;     // Manages the current indentation level in the XML file
    private FileWriter fileWriter;

    /**
     * Creates (or overwrites) the specified XML file and writes the root tag &lt;routes&gt;.
     *
     * @param fileName the name (or path) of the file where the XML will be written.
     * @throws IOException if the file cannot be opened or created for writing.
     */
    public RouteXMLWriter(String fileName) throws IOException {
        this.fileWriter = new FileWriter(new File(fileName));
        // Write the opening root tag
        fileWriter.write("<routes>\n");
        tabAmount++;
    }

    /**
     * Writes a single Route object to the file as a &lt;route&gt; element.
     *
     * @param route the Route object to write.
     * @throws IOException if an error occurs during file writing.
     */
    public void writeRoute(Route route) throws IOException {
        // Open <route> tag
        fileWriter.write(openTag("route") + "\n");
        tabAmount++;

        // Write <id>
        fileWriter.write(tagWithValue("id", String.valueOf(route.getId())) + "\n");

        // Write <name>
        fileWriter.write(tagWithValue("name", route.getName()) + "\n");

        // Write <coordinates>
        fileWriter.write(openTag("coordinates") + "\n");
        tabAmount++;
        fileWriter.write(tagWithValue("x", route.getCoordinates().getX().toString()) + "\n");
        fileWriter.write(tagWithValue("y", String.valueOf(route.getCoordinates().getY())) + "\n");
        tabAmount--;
        fileWriter.write(closeTag("coordinates") + "\n");

        // Write <from> location
        fileWriter.write(openTag("from") + "\n");
        tabAmount++;
        fileWriter.write(tagWithValue("x", route.getFrom().getX().toString()) + "\n");
        fileWriter.write(tagWithValue("y", String.valueOf(route.getFrom().getY())) + "\n");
        fileWriter.write(tagWithValue("name", route.getFrom().getName()) + "\n");
        tabAmount--;
        fileWriter.write(closeTag("from") + "\n");

        // Write <distance>
        fileWriter.write(tagWithValue("distance", String.valueOf(route.getDistance())) + "\n");

        // Write <to> location, if it exists
        if (route.getTo() != null) {
            fileWriter.write(openTag("to") + "\n");
            tabAmount++;
            fileWriter.write(tagWithValue("x", route.getTo().getX().toString()) + "\n");
            fileWriter.write(tagWithValue("y", String.valueOf(route.getTo().getY())) + "\n");
            fileWriter.write(tagWithValue("name", route.getTo().getName()) + "\n");
            tabAmount--;
            fileWriter.write(closeTag("to") + "\n");
        }

        // Close </route> tag
        tabAmount--;
        fileWriter.write(closeTag("route") + "\n");
    }

    /**
     * Writes all Route objects from the provided list to the XML file.
     *
     * @param routes a list of Route objects to be written to XML.
     * @throws IOException if an error occurs during file writing.
     */
    public void writeRoutes(LinkedList<Route> routes) throws IOException {
        for (Route route : routes) {
            writeRoute(route);
        }
    }

    /**
     * Completes the XML file by writing the closing root tag &lt;/routes&gt; and releasing the file writing resource.
     * This method should be called after all Route objects have been written.
     *
     * @throws IOException if an error occurs during file writing.
     */
    @Override
    public void close() throws IOException {
        // Write the closing root tag
        fileWriter.write("</routes>\n");
        fileWriter.close();
    }


    /**
     * Returns an opening tag string with the appropriate indentation based on the current nesting level.
     *
     * @param tag the tag name.
     * @return the formatted opening tag string.
     */
    private String openTag(String tag) {
        return getIndent() + "<" + tag + ">";
    }

    /**
     * Returns a closing tag string with the appropriate indentation based on the current nesting level.
     *
     * @param tag the tag name.
     * @return the formatted closing tag string.
     */
    private String closeTag(String tag) {
        return getIndent() + "</" + tag + ">";
    }

    /**
     * Returns a string containing an opening and closing tag with the provided value:
     * <tag>value</tag>, considering the current indentation level.
     *
     * @param tag   the tag name.
     * @param value the value to be enclosed within the tag.
     * @return the formatted string with the tag and value.
     */
    private String tagWithValue(String tag, String value) {
        return getIndent() + "<" + tag + ">" + value + "</" + tag + ">";
    }

    /**
     * Generates a string of spaces for the current indentation level.
     * For example, each level is indented by 4 spaces.
     *
     * @return a string representing the current indentation.
     */
    private String getIndent() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tabAmount; i++) {
            sb.append("    "); // 4 spaces per indentation level
        }
        return sb.toString();
    }
}
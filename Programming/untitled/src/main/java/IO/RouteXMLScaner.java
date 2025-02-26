package IO;

import itemsInArrea.Coordinates;
import itemsInArrea.Location;
import itemsInArrea.Route;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * The RouteXMLScaner class parses an XML file to extract Route objects.
 * <p>
 * This class uses a Scanner to read an XML file containing route data and builds a list
 * of {@link Route} objects. It supports scanning of nested elements like coordinates and locations.
 * </p>
 */
public class RouteXMLScaner {
    /**
     * List of routes parsed from the XML file.
     */
    LinkedList<Route> routes;

    /**
     * The file to be scanned.
     */
    File file;

    /**
     * Scanner used for reading the file.
     */
    Scanner scan;

    /**
     * Current XML tag being processed (not actively used in parsing).
     */
    String curentTag;

    /**
     * Flag to control location scanning when a destination location ("to") is present.
     */
    boolean flag;

    /**
     * Constructs a new RouteXMLScaner for the given file.
     *
     * @param file the XML file to parse.
     * @throws FileNotFoundException if the file cannot be found.
     */
    public RouteXMLScaner(File file) throws FileNotFoundException {
        this.file = file;
        this.routes = new LinkedList<Route>();
        try {
            this.scan = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.err.println("File not found, try to reboot the program with other arguments");
            throw e;
        }
    }

    /**
     * Reads data from the XML file and returns a list of Route objects.
     * <p>
     * This method assumes the XML has a certain structure and repeatedly skips irrelevant lines.
     * It reads each route's id, name, coordinates, starting location, and optionally the destination location.
     * </p>
     *
     * @return a LinkedList of Route objects parsed from the file.
     */
    public LinkedList<Route> readData() {
        scipUselessLine(1);
        while (scan.hasNextLine()) {
            scipUselessLine(1);
            if (scan.hasNextLine()) {
                String line = scan.nextLine();
                if (!line.equals("</route>")) {
                    long curId = Long.parseLong(getStrBetweenTags(scan.nextLine()));
                    String curRouteName = getStrBetweenTags(scan.nextLine());
                    Coordinates curCoords = scanCoordinates();
                    Location from = scanLocation();
                    long dis = Long.parseLong(getStrBetweenTags(scan.nextLine()));
                    if (scan.nextLine().trim().equals("<to>")) {
                        flag = true;
                        Location to = scanLocation();
                        routes.add(new Route(curId, curRouteName, curCoords, from, dis, to));
                        flag = false;
                    } else {
                        routes.add(new Route(curId, curRouteName, curCoords, from, dis));
                    }
                    scipUselessLine(1);
                }
            }
        }
        scan.close();
        return routes;
    }

    /**
     * Skips a specified number of lines from the input.
     *
     * @param n the number of lines to skip.
     */
    public void scipUselessLine(int n) {
        for (int i = 0; i < n; i++) {
            if (scan.hasNextLine()) {
                scan.nextLine();
            }
        }
    }

    /**
     * Scans and returns a Location object from the XML file.
     * <p>
     * Depending on the flag, it may skip a line before reading.
     * It reads the x coordinate, y coordinate, and name of the location,
     * then constructs a {@link Location} object.
     * </p>
     *
     * @return a Location extracted from the XML.
     */
    public Location scanLocation() {
        if (!flag) {
            scipUselessLine(1);
        }
        Float curX = Float.valueOf(getStrBetweenTags(scan.nextLine()));
        long curY = Long.parseLong(getStrBetweenTags(scan.nextLine()));
        String curLocationName = getStrBetweenTags(scan.nextLine());
        Location location = new Location(curX, curY, curLocationName);
        scipUselessLine(1);
        return location;
    }

    /**
     * Scans and returns a Coordinates object from the XML file.
     * <p>
     * It skips an irrelevant line, then reads the x and y coordinates, and finally skips
     * another line.
     * </p>
     *
     * @return a Coordinates object containing x and y values.
     */
    public Coordinates scanCoordinates() {
        scipUselessLine(1);
        Coordinates curCoor = new Coordinates();
        curCoor.setX(Float.valueOf(getStrBetweenTags(scan.nextLine())));
        curCoor.setY(Float.parseFloat(getStrBetweenTags(scan.nextLine())));
        scipUselessLine(1);
        return curCoor;
    }

    /**
     * Extracts and returns the text between XML tags from a given line.
     * <p>
     * It iterates through the characters of the line, starting to capture characters immediately after
     * encountering a '>' character until a '<' is encountered.
     * </p>
     *
     * @param line the line of XML to process.
     * @return the substring between the first pair of XML tags.
     */
    public String getStrBetweenTags(String line) {
        boolean flagIn = false;
        StringBuilder sb = new StringBuilder();
        char[] arr = line.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == '>') {
                flagIn = true;
            }
            if (flagIn && i + 1 < arr.length && arr[i + 1] == '<') {
                flagIn = false;
                return sb.toString();
            }
            if (flagIn && i + 1 < arr.length) {
                sb.append(arr[i + 1]);
            }
        }
        return sb.toString();
    }

    /**
     * Determines if a line from the XML file contains useful content.
     * <p>
     * A useful line is defined as one that contains more than two angle bracket characters.
     * </p>
     *
     * @param line the line to check.
     * @return true if the line is considered useful, false otherwise.
     */
    public static boolean lineIsUseful(String line) {
        char[] area = line.toCharArray();
        int k = 0;
        for (int i = 0; i < area.length; i++) {
            if (area[i] == '>' || area[i] == '<') {
                k++;
            }
        }
        return k > 2;
    }
}
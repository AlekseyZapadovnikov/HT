package IO;

import itemsInArrea.Coordinates;
import itemsInArrea.Location;
import itemsInArrea.Route;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;


public class RouteXMLScaner {
    LinkedList<Route> routes;
    File file;
    Scanner scan;
    String curentTag;
    boolean flag;

    public RouteXMLScaner(File file) throws FileNotFoundException {
        this.file = file;
        this.routes = new LinkedList<Route>();
        try {
            this.scan = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("file not found, try to reboot the program with another arguments");
            throw e;
        }
    }

    public LinkedList<Route> readData() {
        scipUselessLine(1);
        while (scan.hasNextLine()) {
            scipUselessLine(1);
            if (scan.hasNextLine()){
                String line = scan.nextLine();
            if (!line.equals("</route>")){
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
                }else {
                    routes.add(new Route(curId, curRouteName, curCoords, from, dis));
                }
                scipUselessLine(1);
            }
            }
        }scan.close();
        return routes;
    }

    public void scipUselessLine(int n){
        for (int i = 0; i < n; i++){
            scan.nextLine();
        }
    }

    public Location scanLocation(){
        if (!flag) {scipUselessLine(1);}
        Float curX = Float.valueOf(getStrBetweenTags(scan.nextLine()));
        long curY = Long.parseLong(getStrBetweenTags(scan.nextLine()));
        String curLocationName = getStrBetweenTags(scan.nextLine());
        Location location = new Location(curX, curY, curLocationName);
        scipUselessLine(1);
        return location;
    }

    public Coordinates scanCoordinates(){
        scipUselessLine(1);
        Coordinates curCoor = new Coordinates();
        curCoor.setX(Float.valueOf(getStrBetweenTags(scan.nextLine())));
        curCoor.setY(Float.parseFloat(getStrBetweenTags(scan.nextLine())));
        scipUselessLine(1);
        return curCoor;
    }


    public String getStrBetweenTags(String line){
        boolean flagIn = false;
        StringBuilder sb = new StringBuilder();
        char[] arr = line.toCharArray();
        for (int i = 0; i < arr.length; i++){
            if (arr[i] == '>'){
                flagIn = true;
            }
            if (flagIn && arr[i + 1] == '<'){
                flagIn = false;
                return sb.toString();
            }
            if (flagIn){
                sb.append(arr[i+1]);
            }
        }
        return sb.toString();
    }

    public static boolean lineIsUseful(String line){
        char[] area = line.toCharArray();
        int k = 0;
        for (int i = 0; i < area.length; i++){
            if (area[i] == '>' || area[i] == '<'){
                k++;
            }
        }
        if (k > 2){
            return true;
        }
        return false;
    }
}

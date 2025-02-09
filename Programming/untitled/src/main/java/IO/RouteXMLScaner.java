package IO;

import itemsInArrea.Route;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;


public class RouteXMLScaner {
    LinkedList<Route> routes;
    File file;
    Scanner scan;

    public RouteXMLScaner(File file)  {
        this.file = file;
        this.routes = new LinkedList<Route>();
        try {
            this.scan = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("file not found, try to reboot the program with another arguments");
            throw new RuntimeException(e);
        }
    }

    public boolean readDate(){
        while
    }


    public String getStrBetweenTags(String line){
        boolean flagIn = false;
        StringBuilder sb = new StringBuilder();
        char[] arr = line.toCharArray();
        if (lineIsUseful(line)){
            for (int i = 0; i < arr.length; i++){
                if (arr[i] == '>'){
                    flagIn = true;
                }
                if (flagIn && arr[i] == '<'){
                    flagIn = false;
                    return sb.toString();
                }
                if (flagIn){
                    sb.append(arr[i]);
                }
            }
        }
        return "";
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

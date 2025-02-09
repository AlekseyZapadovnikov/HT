package IO;

import itemsInArrea.Route;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class RouteXMLScaner {
    LinkedList<Route> routes = new LinkedList<Route>();
    File file;

    public RouteXMLScaner(File file){
        this.file = file;
    }
    public String getStrBetweenTags(String line){
        if (lineIsUseful(line)){
            String[] arr = line.split(">");
            
        }

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

package org.example;

import IO.RouteXMLScaner;
import IO.RouteXMLWriter;
import itemsInArrea.Route;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        RouteXMLScaner scaner = new RouteXMLScaner(new File("src\\main\\resources\\" + "prog_lab_5_data.xml")); // args[args.lenth - 1]
        LinkedList<Route> sp = scaner.readData();
        RouteXMLWriter writer = new RouteXMLWriter("src\\main\\resources\\test.xml");
        writer.writeRoutes(sp);
        writer.close();
    }
}
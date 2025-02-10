package org.example;

import IO.RouteXMLScaner;
import java.io.File;
import java.io.FileNotFoundException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        RouteXMLScaner scaner = new RouteXMLScaner(new File("C:\\Users\\Asus\\Desktop\\repo\\HT\\Programming\\untitled\\src\\main\\resources\\prog_lab_5_data.xml"));
        System.out.println(scaner.readData());
    }
}
package org.example;
import itemsInArrea.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String line = "I vant to check split_funktion";
        String[] sp = line.split(" ");
        for (int i = 0; i< sp.length; i++){
            System.out.println(sp[i]);
        }
    }


}
package org.example;

import IO.Console;
import managers.ClientConnectionManager;

import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Console console = new Console();
        ClientConnectionManager connectionManager;
        try {
            connectionManager = new ClientConnectionManager("localhost", 1111);
            connectionManager.connect();

        } catch (IOException e) {
            console.println("Ошибка подключения: " + e.getMessage());
        }
    }
}
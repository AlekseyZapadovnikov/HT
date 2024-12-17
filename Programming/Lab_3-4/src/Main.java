import creatures.*;
import other.*;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        Scanner fileScanner = new Scanner(new File("ReadMe.txt"));
        Scanner inputScanner = new Scanner(System.in);
        Random rand = new Random();
        Denford DenExp;
        Dyer DyerExp;
        int[] DenfordLst = new int[4];
        int[] DyerLst = new int[4];
        int k = 0;
        while (fileScanner.hasNextLine() && (k++ < 9)) {
            System.out.println(fileScanner.nextLine());
        }
        if ("YES".equals(inputScanner.next())) {
            DenExp = new Denford(80, 65, 85);
            DyerExp = new Dyer(90, 90, 50);
        } else {
            System.out.println(fileScanner.nextLine());
            for (int i = 0; i < 7; i++) {
                if (i != 3) {
                    System.out.println(fileScanner.nextLine());
                    try {
                        int certStat = inputScanner.nextInt();
                        numCheck(certStat);
                        if (i < 3) {
                            DenfordLst[i] = certStat;
                        } else {
                            DyerLst[i - 4] = certStat;
                        }
                    } catch (LargeNumberException e) {
                        System.out.println("Ошибка: " + e.getMessage());
                    }
                } else {
                    System.out.println(fileScanner.nextLine());
                }
            }
            DenExp = new Denford(DenfordLst);
            DyerExp = new Dyer(DyerLst);
        }
        int anderstanding = 0;
        while (true) {
            Stone stone = new Stone(rand.nextInt() % 100, rand.nextInt() % 100, rand.nextInt() % 100);
            DenExp.takeStone(stone);
            DyerExp.takeStone(stone);
            anderstanding += (DenExp.getAnderstanding() + DyerExp.getAnderstanding());
            if (anderstanding > 400) {
                System.out.println("Совместными усилиями получилось определить примерный возраст города");
                break;
            }
            Animal[] animals = new Animal[]{new Crown(35, 60), new Dog(60, 40)};
            DenExp.lookAround(animals);
            if (DenExp.condition()) {
                System.out.println("Денфорд сошёл с ума");
                break;
            }
            DyerExp.lookAround(animals);
            if (DyerExp.condition()) {
                System.out.println("Дэер сошёл с ума");
                break;
            }
        }
        System.out.println("Конец! =)");
    }

    public static void numCheck(int num) throws LargeNumberException {
        if (Math.abs(num) > 100) {
            throw new LargeNumberException("Все статы от [0:100], для корректности стоит перезапустить программу");
        }
    }
}
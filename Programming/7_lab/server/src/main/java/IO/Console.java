package IO;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Ввод-вывод в консоль
 */
public class Console {
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Вывод без переноса
     * @param obj объект для вывода
     */
    public void print(Object obj){
        System.out.print(obj);
    }

    /**
     * Вывод с переносом
     * @param obj объект для вывода
     */
    public void println(Object obj){
        System.out.println(obj);
    }

    public void warn(String message){
        System.err.println(message);
    }

    /**
     * Считывает строку из потока ввода
     * NoSuchElementException если введено ctrl+C
     * NullPointerException если ничего не введено
     * @return считанная строка
     */

    public String input() throws IOException{
        print("> ");
        try{
            String inp = scanner.nextLine();
            return inp == null || inp.isEmpty() ? null : inp.trim();
        }catch (NoSuchElementException e){
            throw new IOException();
        }catch (NullPointerException e){
            return null;
        }
    }

    public Long parseLong(String line){
        if(line == null){
            return null;
        }
        return Long.parseLong(line);
    }

    public Double parseDouble(String line){
        if(line == null){
            return null;
        }
        return Double.parseDouble(line);
    }

    public Float parseFloat(String line){
        if(line == null){
            return null;
        }
        return Float.parseFloat(line);
    }

    public Integer parseInteger(String line){
        if(line == null){
            return null;
        }
        return Integer.parseInt(line);
    }
}

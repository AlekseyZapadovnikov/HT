package IO;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Организует чтение данных из файла
 */
public class FileReader implements Reader {
    private File file;
    private Scanner reader;


    public FileReader(String fileName) throws FileNotFoundException {
        this.file = new File(fileName);
        this.reader = new Scanner(file);
    }

    public String nextLine(){
        try{
            return reader.nextLine().trim();
        }catch (NoSuchElementException e){
            return null;
        }
    }

    public String read(){
        String s = "";
        while (true){
            try{
                s += reader.nextLine().trim();
            }catch (NoSuchElementException e){
                break;
            }
        }
        return s;
    }
}

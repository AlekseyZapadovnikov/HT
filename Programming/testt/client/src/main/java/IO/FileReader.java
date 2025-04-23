package IO;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Организует чтение данных из файла
 */
public class FileReader {
    private File file;
    private Scanner reader;
    private final String SURCE_PATH = "client\\src\\main\\resources\\";


    public FileReader(String fileName) throws FileNotFoundException {
        File file = new File(SURCE_PATH + fileName);
        reader = new Scanner(file);
    }

    public boolean hasNextLine(){
        return reader.hasNextLine();
    }

    public Request readCommand(){
        String line = reader.nextLine();
        Request fileRequest = Console.parseLine(line);
        return fileRequest;
    }

    public String nextLine() {
        return reader.nextLine();
    }

}

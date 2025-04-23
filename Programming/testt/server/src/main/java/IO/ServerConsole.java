package IO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerConsole extends Console {
    private final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private final Logger logger = Logger.getLogger("ServerLogger");

    public String bufferedRead() throws IOException {
        return bufferedReader.readLine();
    }

    public void log(Level level, String message){
        logger.log(level, message);
    }
}

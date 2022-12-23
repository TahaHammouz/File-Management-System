package Logger;

import java.io.*;
import java.util.ArrayList;

public class Logger {

    private static Logger instance;
    static ArrayList<String> logsArray = new ArrayList<String>(5);

    private Logger() {}

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public static void logInfo(String message) {
        logsArray.add(java.time.LocalDateTime.now() + " [INFO] " + message);
    }

    public static void logDebug(String message) {
        logsArray.add(java.time.LocalDateTime.now() + " [Debug] " + message);
    }

    public static void logWarning(String message) {
        logsArray.add(java.time.LocalDateTime.now() + " [Warn] " + message);
    }

    public static void logError(String message) {
        logsArray.add(java.time.LocalDateTime.now() + " [Error] " + message);
    }

    public static void SaveLogs() {
        try {
            FileWriter out = new FileWriter("./logs.txt", true);
            for (int i = 0; i < logsArray.size(); i++) out.write(
                    logsArray.get(i) + "\n"
            );

            out.close();
        } catch (IOException ex) {
            System.out.println("IOException: " + ex.getMessage());
        }
    }
}

package Controller;

import Connection.Database;
import Crypto.Encryption;
import Logger.Logger;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static Constants.Constants.*;

public class Post {
    static Path path = null;

    public Post() {    }

    public static void importFile() throws Exception {
        path = Path.of((file_path()));
        System.out.println(path);
        if (Files.exists(path) ) {
            Database.insertFile(Encryption.encrypt(String.valueOf(path.getFileName())), file_category(), file_size(), path, file_custom());
        }else {
            System.out.println("Try again !!");
        }

    }

    private static String file_custom() throws IOException {

        long bytes = Files.size(path);
        if (bytes < KILOBYTE) {
            return SMALL;
        } else if (bytes < MEGABYTE) {
            return MEDIUM;
        }
        return LARGE;

    }

    private static String file_path() {
        JFileChooser file = new JFileChooser();
        file.setMultiSelectionEnabled(true);
        file.setFileSelectionMode(JFileChooser.FILES_ONLY);
        file.setFileHidingEnabled(false);
        try {
            return check_file_path(file);

        } catch (Exception e) {
            Logger.logWarning("the user does not choose any file");
            return e.getMessage();
        }

    }
    private static String check_file_path(JFileChooser file) throws Exception {
        if (file.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File f = file.getSelectedFile();
            return f.getPath();
        } else {
            throw new Exception("you haven't attach any file");
        }
    }


        private static String file_size() {
        try {
            long bytes = Files.size(path);
            if (bytes < KILOBYTE) {
                return String.format("%,d bytes", (int)bytes);
            } else {
                return bytes < MEGABYTE ? String.format("%,d kilobytes", (int)bytes / KILOBYTE) : String.format("%,d megabytes", (int)bytes / MEGABYTE);
            }
        } catch (IOException var2) {
            System.out.println("An error occurred while getting the file size: " + var2.getMessage());
            return null;
        }
    }

    public static String file_category() throws NullPointerException {
        try {
            return Files.probeContentType(path);
        } catch (NullPointerException var1) {
            return "this category is not found in files";
        } catch (IOException var2) {
            throw new RuntimeException(var2);
        }
    }
}

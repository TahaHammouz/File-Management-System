//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Controller;

import Connection.Database;
import Crypto.Encryption;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Objects;
import javax.swing.JFileChooser;

public class Post {
    static Path path = null;
    private static final int kilobyte = 1024;
    private static final int megabyte = (1024*1024);

    public Post() {    }

    public static void importFile() throws Exception {
        path = Path.of(Objects.requireNonNull(file_path()));
        if (Files.exists(path)) {
            Database.insertFile(Encryption.encrypt(String.valueOf(path.getFileName())), file_category(), file_size(), path, file_custom());
        } else {
            System.out.println("nah");
        }

    }

    private static String file_custom() {
        try {
            long bytes = Files.size(path);
            if (bytes < kilobyte) {
                return "small";
            } else {
                return bytes < megabyte ? "medium" : "large";
            }
        } catch (IOException var2) {
            System.out.println("An error occurred while getting the file custom: " + var2.getMessage());
            return null;
        }
    }

    private static String file_path() {
        JFileChooser file = new JFileChooser();
        file.setMultiSelectionEnabled(true);
        file.setFileSelectionMode(2);
        file.setFileHidingEnabled(false);
        if (file.showOpenDialog(null) == 0) {
            File f = file.getSelectedFile();
            return f.getPath();
        } else {
            return null;
        }
    }

    private static String file_size() {
        try {
            long bytes = Files.size(path);
            if (bytes < kilobyte) {
                return String.format("%,d bytes", (int)bytes);
            } else {
                return bytes < megabyte ? String.format("%,d kilobytes", (int)bytes / kilobyte) : String.format("%,d megabytes", (int)bytes / megabyte);
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

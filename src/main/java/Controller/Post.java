//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Controller;

import Connection.Database;
import Crypto.Encryption;
import Exception.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import javax.swing.JFileChooser;

import FileReporsitory.FileRepository;
import Logger.Logger;

import static Constants.Constants.*;

public class Post {
    static Path path = null;

    public static void importFile() throws Exception {
        try{
            path = Path.of(Objects.requireNonNull(file_path()));
            File file = new File(path.toUri());
            FileRepository.addFile(file);
            if (Files.exists(path)) {
                Database.insertFile(Encryption.encrypt(String.valueOf(path.getFileName())), file_category(),
                        file_size(), path, file_custom());
            }
        }catch(PostException e){
            System.out.println(e.getMessage());
            Logger.logInfo("user haven't attached any file");
        }
    }

    public static String file_custom() throws Exception {
        try {
            long bytes = Files.size(path);
            if (bytes < kilobyte) {
                return "small";
            } else {
                return bytes < megabyte ? "medium" : "large";
            }
        } catch (Exception e) {
            throw new Exception("An error occurred while getting the file custom" + e.getMessage());
        }
    }

    private static String file_path() throws PostException {
        JFileChooser file = new JFileChooser();
        file.setMultiSelectionEnabled(true);
        file.setFileSelectionMode(2);
        file.setFileHidingEnabled(false);
        if (file.showOpenDialog(null) == 0) {
            File f = file.getSelectedFile();
            return f.getPath();
        } else {
            throw new PostException("You haven't attached any file !");
        }
    }

    public static String file_size() throws Exception {
        try {
            long bytes = Files.size(path);
            if (bytes < kilobyte) {
                return String.format("%,d bytes", (int)bytes);
            } else {
                return bytes < megabyte ? String.format("%,d kilobytes", (int)bytes / kilobyte) : String.format("%,d megabytes", (int)bytes / megabyte);
            }
        } catch (Exception e) {
            throw new Exception("An error occurred while getting the file size:" + e.getMessage());

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

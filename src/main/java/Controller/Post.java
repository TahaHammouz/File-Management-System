package Controller;

import Connection.Database;
import Crypto.Encryption;
import Exception.*;

import Logger.Logger;

import javax.swing.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static Constants.Constants.*;

import FileReporsitory.FileRepository;


public class Post {
    static Path path = null;

    public static void importFile() throws Exception {

        path = Path.of(Objects.requireNonNull(file_path()));
        File file = new File(path.toUri());
        FileRepository.addFile(file);
        try {
            if (Files.exists(path)) {
                Database.insertFile(Encryption.encrypt(String.valueOf(path.getFileName())), file_type() ,
                        file_category(),file_size(), path, file_custom());
            }
        } catch (PostException e) {
            System.out.println(e.getMessage());
            Logger.logInfo("user haven't attached any file");
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
        file.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
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
            throw new PostException("You haven't attached any file !");
        }
    }
    private static String file_size() throws Exception {
        try {
            long bytes = Files.size(path);
            if (bytes < KILOBYTE) {
                return String.format("%,d bytes", (int)bytes);
            } else {
                return bytes < MEGABYTE ? String.format("%,d kilobytes", (int)bytes / KILOBYTE) : String.format("%,d megabytes", (int)bytes / MEGABYTE);
            }
        } catch (Exception e) {
            throw new Exception("An error occurred while getting the file size:" + e.getMessage());

        }
    }

    public static String file_category() throws NullPointerException {
        try {
            return Files.probeContentType(path);
        } catch (NullPointerException var1) {
            return "This category is not found in files";
        } catch (IOException var2) {
            throw new RuntimeException(var2);
        }
    }
    private static String file_type() {
        String fileName = path.toString();
        int sep = fileName.lastIndexOf(".");

        return fileName.substring(sep + 1);
    }
}

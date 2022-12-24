package FileReporsitory;
import Controller.Get;
import Crypto.Decryption;
import org.w3c.dom.events.EventListener;

import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileRepository {

        public static Map<String, File> files = new HashMap<>();
        // Constructor and other methods omitted for brevity

        public static void addFile(File file) {
            // Store the file in the repository
            System.out.println("add from file repo");
            files.put(file.getName(), file);
        }
        public static void removeFile(File file) {
            // Store the file in the repository
            files.remove(file.getName(), file);
        }

        public static void getFile(String name) throws Exception {
            System.out.println("get from file repo");
            // Retrieve the file from the repository
            String homeDirectory = System.getProperty("user.home");
            Path downloadPath = Paths.get(homeDirectory, "Downloads");

            Path sourcePath = Path.of(files.get(name).getPath());
            Path targetPath = Path.of(downloadPath + "/" + name);

            if (Files.exists(sourcePath)) {
                try {
                    Files.createFile(targetPath);
                }catch (Exception e){
                    Files.createFile(Path.of(targetPath + "(1)"));
                }
                Files.copy(targetPath, sourcePath);

                if (Files.exists(targetPath)) {
                    System.out.println("\nExported File Successfully: " + targetPath);
                }
            }


        }
    }




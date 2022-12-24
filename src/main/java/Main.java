import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) throws Exception {
//        Post.importFile();
//        String name = "file.txt";
//        String EName;
//
//        System.out.println(Encryption.encrypt(name));
//        EName = Encryption.encrypt(name);
//        System.out.println(Decryption.decrypt(EName));
//
//        Path path = Path.of("C:\\Users\\AbedelAziz Kharobi\\Downloads\\test.txt");
//        if (Files.exists(path)) {
//              Read the file's data into a byte array
////            byte[] fileData = Files.readAllBytes(path);
////            String fileContent = new String(fileData);
////
////            // Print the file content
////            System.out.println(fileContent);
//            Files.lines(path).forEach(System.out::println);
//        }

//            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//            String filename;
//            while (true){
//                System.out.println("Enter the file name to download");
//                filename = reader.readLine();
//                if (filename.equals(targetPath.getFileName().toString())){
//                    break;
//                }
//                System.out.println("incorrect file name");
//            }
//
//        }


//        Path downloadPath = Paths.get("/path/to/downloads");
//
//        System.out.println("Download path: " + downloadPath);
//        String homeDirectory = System.getProperty("user.home");
//
//// Append the "downloads" directory to the home directory
//        Path downloadPath = Paths.get(homeDirectory, "downloads");
//
//// Print the download path
//        System.out.println("Download path: " + downloadPath);
//
////         Define a variable and set its value
//        int x = -5;
//
//        // Make an assertion about the value of x
//        assert x > 0 : "x is not positive";
//
//        // The code execution will not reach here because the assertion is false
//        System.out.println("x is positive");
//
//
//        Path sourcePath = Path.of("C:\\Users\\AbedelAziz Kharobi\\Downloads\\test.txt");
//        Path targetPath = Path.of(downloadPath+"/zaha.txt");
//        if (Files.exists(sourcePath)) {
//            List<String> lines = Files.readAllLines(sourcePath);
//
//            Files.createFile(targetPath);
//
//            Files.write(targetPath, lines);
//
//            if (Files.exists(targetPath)) {
//                System.out.println("File successfully" + targetPath.toString());
//            }
//        }

        JFrame frame = new JFrame("My Frame");

        
        // Create the OK button
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            // Do something when the OK button is clicked
            System.out.println("OK button was clicked!");
        });

        // Create the Cancel button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            // Do something when the Cancel button is clicked
            System.out.println("Cancel button was clicked!");
        });

        // Add the buttons to the frame
        frame.add(okButton, BorderLayout.WEST);
        frame.add(cancelButton, BorderLayout.EAST);

        frame.pack();
        frame.setVisible(true);
    }
}
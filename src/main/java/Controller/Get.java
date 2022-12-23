package Controller;

import java.util.Scanner;
import Crypto.Encryption;
import static Connection.Database.retrieveFile;

public class Get {

    public static void exportFile() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter File Name (with extension) to Export: ");
        String fileName = scanner.nextLine();

        retrieveFile(Encryption.encrypt(fileName));

        //System.out.println("Done, the file is exported.");
        //System.out.println("The file is not exist.");
    }
}
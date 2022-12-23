package Controller;

import java.util.Scanner;
import Crypto.Encryption;
import static Connection.Database.retrieveFile;

public class Get {

    public static void exportFile() throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nEnter File Name (with extension) to Export:");
        String fileName = scanner.nextLine();

        System.out.println("Enter File Custom to Export:");
        String custom = scanner.nextLine();

        retrieveFile(Encryption.encrypt(fileName), custom);
    }
}
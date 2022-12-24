package Controller;

import java.util.Scanner;
import Crypto.Encryption;
import FileReporsitory.FileRepository;

import static Connection.Database.retrieveFile;
import static FileReporsitory.FileRepository.files;

public class Get {

    public static void exportFile() throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nEnter File Name (with extension) to Export:");
        String fileName = scanner.nextLine();

        System.out.println("What is the file size ? small, medium or large?:");
        String custom = scanner.nextLine();

        if(files.containsKey(fileName)){
            System.out.println("get file from hashmap");
            FileRepository.getFile(fileName);
        }else {
            System.out.println("get file from db");
            retrieveFile(Encryption.encrypt(fileName), custom);

        }









    }
}
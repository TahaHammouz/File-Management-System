package Connection;

import Crypto.Decryption;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import static Connection.Database.getConnection;

public class ClasseficationFiles {

    public static void classifyFiles() {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        while (choice != 4) {

            System.out.println("\n***********************************************");
            menu();
            System.out.println("\nEnter you choice:");
            choice = scanner.nextInt();
            System.out.println("***********************************************");

            switch (choice) {
                case 1:
                    classifyType();
                    break;
                case 2:
                    classifySize();
                    break;
                case 3:
                    classifyCustom();
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public static void classifyType() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nEnter File Type to Classefication:");
        String type = scanner.nextLine();

        final String query = "SELECT * FROM files WHERE type = ?";

        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, type);
            ResultSet rs = preparedStatement.executeQuery();

            System.out.println("The file names for this classify:");
            while (rs.next()) {
                System.out.println(Decryption.decrypt(rs.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void classifySize() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nEnter File Size to Classefication (*** note: enter size with bytes, kilobytes, or megabytes):");
        String size = scanner.nextLine();

        final String query = "SELECT * FROM files WHERE size = ?";

        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, size);

            ResultSet rs = preparedStatement.executeQuery();

            System.out.println("The file names for this classify:");
            while (rs.next()) {
                System.out.println(Decryption.decrypt(rs.getString("name")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void classifyCustom() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nEnter File Custom (small, medium, or large) to Classefication:");
        String custom = scanner.nextLine();

        final String query = "SELECT * FROM files WHERE custom = ?";

        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, custom);

            ResultSet rs = preparedStatement.executeQuery();

            System.out.println("The file names for this classify:");
            while (rs.next()) {
                System.out.println(Decryption.decrypt(rs.getString("name")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void menu() {
        System.out.println("Want to make a classification of files through:\n");
        System.out.println("    1. Type File");
        System.out.println("    2. Size File");
        System.out.println("    3. Custom category");
        System.out.println("    4. Stop");
    }
}
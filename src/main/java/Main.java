import Connection.ClassificationFiles;
import Connection.Database;
import Controller.Post;
import Authentication.AddUser;
import Authentication.Login;
import Authentication.User;
import Controller.Delete;
import Controller.Get;
import Exception.AuthException;
import Logger.Logger;

import java.sql.SQLException;
import java.util.Scanner;

import static Connection.Database.viewFiles;


public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static String username;
    public static String password;
    public static boolean isAdmin;
    public static boolean isStaff;
    public static String position;
    public static User user;

    public static void main(String[] args) throws Exception {
        new Database();
        System.out.println("Welcome to my project ");
        System.out.println("Here we wont to help you in file management  ");

        while(true) {
            System.out.println("--- Sign In or Sign Up ---");
            System.out.println("1. Sign in");
            System.out.println("2. Sign up");
            System.out.println("Enter the number of the option you would like to choose:");
            int choice = scanner.nextInt();
//            int choice = 1;
            switch (choice) {
                case 1:
                    user = signin();
                    break;
                case 2:
                    user = signup();
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }

            menu(user.getRole());
        }
    }

    public static void menu(String position) throws Exception {
        if (position.equals("admin")) {
            isAdmin = true;
            isStaff = true;
        } else if (position.equals("staff")) {
            isStaff = true;
        }

        while(true) {
            System.out.println("--- File Management System Menu ---");
            System.out.println("1. View file");
            System.out.println("2. classification file");
            if (isStaff) {
                System.out.println("3. Import file");
                System.out.println("4. Export file");
                System.out.println("5. view logger");
            }

            if (isAdmin) {
                System.out.println("6. Delete file");
            }

            System.out.println("7. Log out");
            System.out.println("Enter the number of the option you would like to choose:");
            int choice = scanner.nextInt();
//            int choice = 1;
            switch (choice) {
                case 1:
                    viewFiles();
                    break;
                case 2:
                    ClassificationFiles.classifyFiles();
                    break;
                case 3:
                    Post.importFile();
                    break;
                case 4:
                    Get.exportFile();
                    break;
                case 5:
                    Logger.SaveLogs();
                    break;
                case 6:
                    Delete.deleteFile();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public static User signup(){
        System.out.println("Enter your user name");
        username = scanner.next();
        System.out.println("Enter your password ");
        password = scanner.next();
        System.out.println("Enter your position ");
        position = scanner.next();

        AddUser.insertUser(username, password, position);
        return new User(username, password, position);
    }

    public static User signin() throws SQLException, AuthException {
        System.out.println("Enter your user name");
        username = scanner.next();
//        username = "abd";
        System.out.println("Enter your password ");
        password = scanner.next();
//        password = "123";
        return Login.loginDatabase(username, password);
    }

    static {
        isAdmin = false;
        isStaff = false;
    }
}

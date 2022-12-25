//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import Authentication.AddUser;
import Authentication.Login;
import Authentication.User;
import Connection.Database;
import Controller.Delete;
import Controller.Get;
import Controller.Post;
import Exception.AuthException;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static String choose;
    public static Scanner scanner;
    public static String username;
    public static String password;
    public static boolean isAdmin;
    public static boolean isStaff;
    public static String position;
    public static User user;

    public Main() {
    }

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
            System.out.println("1. View files");
            System.out.println("2. classification file");
            if (isStaff) {
                System.out.println("3. Import file");
                System.out.println("4. Export file");
            }

            if (isAdmin) {
                System.out.println("5. Delete file");
            }

            System.out.println("6. Log out");
            System.out.println("Enter the number of the option you would like to choose:");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                case 2:
                    break;
                case 3:
                    Post.importFile();
                    break;
                case 4:
                    Get.exportFile();
                    break;
                case 5:
                    Delete.deleteFile();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public static User signup() throws SQLException, AuthException {
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
        System.out.println("Enter your password ");
        password = scanner.next();
        return Login.loginDatabase(username, password);
    }

    static {
        scanner = new Scanner(System.in);
        isAdmin = false;
        isStaff = false;
    }
}

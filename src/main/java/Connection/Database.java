package Connection;

import Crypto.Decryption;
import Crypto.Encryption;
import Logger.Logger;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Objects;
import java.util.Scanner;

import static Constants.Constants.*;
import static FileReporsitory.FileRepository.files;

public class Database {

    public Database() throws ClassNotFoundException {
        // Connect to the database
        Class.forName("org.sqlite.JDBC");
    }
    public static void insertFile(String name, String type, String category,
                                  String size, Path path, String custom) throws Exception {

        final String query = "INSERT INTO files(name, type, category, size, path, custom) VALUES (?, ?, ?, ?, ?, ?)";


        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, String.valueOf(name));
            preparedStatement.setString(2, type);
            preparedStatement.setString(3, category);
            preparedStatement.setString(4, size);
            preparedStatement.setString(5, String.valueOf(path));
            preparedStatement.setString(6, custom);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("File is already exist (R) To replace/(V) to make a version of it, R/V");
            Scanner sc = new Scanner(System.in);
            String choice = sc.next();
            if(Objects.equals(choice, "r") || Objects.equals(choice, "R")){
                //replace
                //1: file repo
                File file = new File(path.toUri());
                if(files.containsKey(file)){
                    files.remove(file);
                    files.put(String.valueOf(name),file);

                }else{
                    files.put(String.valueOf(name),file);
                }

                deleteFile(String.valueOf(path.getFileName()) , custom);
                Database.insertFile(Encryption.encrypt(String.valueOf(path.getFileName())),type,category, size, path, custom);

            } else if (Objects.equals(choice, "v") || Objects.equals(choice, "V")) {
                //versioning
            }
            else{
                System.out.println("Wrong input !!");
                Logger.logWarning("Wrong input in Multi versioning choice ");
            }
        }
    }



    public static void deleteFile(String name , String custom) {
        final String query = "DELETE FROM files WHERE name = ? AND custom = ?";

        try {
            PreparedStatement stmt = getConnection().prepareStatement(query);
            stmt.setString(1, Encryption.encrypt(name));
            stmt.setString(2, custom);
            stmt.executeUpdate();
            Logger.logInfo("row has been deleted " + name + "/" + custom);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static void retrieveFile(String fileName, String custom) {

        final String query = "SELECT path FROM Files WHERE name = ? AND custom = ?";

        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, fileName);
            preparedStatement.setString(2, custom);

            ResultSet rs = preparedStatement.executeQuery();
            rs.next();

            Path path = Paths.get(rs.getString("path"));

            // Get path your download
            String homeDirectory = System.getProperty("user.home");
            Path downloadPath = Paths.get(homeDirectory, "Downloads");

            Path targetPath = Paths.get(downloadPath + "/" + Decryption.decrypt(fileName));

            if (Files.exists(targetPath)) {
                // Prompt the user to choose a new destination path or overwrite the existing file
                Scanner scanner = new Scanner(System.in);
                System.out.print("A file with the same name already exists at the destination path. Do you want to overwrite it? (Y/N) ");
                String answer = scanner.nextLine();
                if (!answer.equalsIgnoreCase("Y")) {
                    System.out.print("Enter a new destination path with exception: ");
                    targetPath = Paths.get(downloadPath + "/" + scanner.nextLine());
                }
            }

            // Copy the file to the destination path
            try {
                Files.copy(path, targetPath);
                System.out.println("File copied successfully.");
            } catch (Exception e) {
                System.out.println("An error occurred while copying the file: " + e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createFilesTable() {
        final String query = "CREATE TABLE IF NOT EXISTS files (" +
                "name text NOT NULL, " +
                "type text NOT NULL, " +
                "category text, " +
                "size text NOT NULL, " +
                "path text NOT NULL," +
                " custom text NOT NULL," +
                " PRIMARY KEY (name, custom)" +
                ")";
        try {
            Statement stmt = getConnection().createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createUserTable() {
        final String query = "CREATE TABLE IF NOT EXISTS users (" +
                "username text PRIMARY KEY NOT NULL, " +
                "password text NOT NULL, " +
                "role text NOT NULL)";

        try {
            Statement stmt = getConnection().createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    static {
        Database.createUserTable();
        Database.createFilesTable();
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(SQLiteURL);
    }
}
package Connection;

import Crypto.Decryption;
import Crypto.Encryption;
import FileReporsitory.FileRepository;
import Logger.Logger;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static Constants.Constants.*;
import static Controller.Post.*;
import static FileReporsitory.FileRepository.files;

public class Database {
    private  Connection connection;

    public Database() throws ClassNotFoundException {
        // Connect to the database
        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection(SQLiteURL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertFile(String name, String category,
                                  String size, Path path, String custom) throws Exception {

        String query = "INSERT INTO files(name, category, size, path, custom) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, String.valueOf(name));
            preparedStatement.setString(2, category);
            preparedStatement.setString(3, size);
            preparedStatement.setString(4, String.valueOf(path));
            preparedStatement.setString(5, custom);
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

                deleteFile(String.valueOf(path.getFileName()));
                Database.insertFile(Encryption.encrypt(String.valueOf(path.getFileName())),file_category(), file_size(), path, file_custom());

            } else if (Objects.equals(choice, "v") || Objects.equals(choice, "V")) {
                //versioning
            }
            else{
                System.out.println("Wrong input !!");
                Logger.logWarning("Wrong input in Multi versioning choice ");
            }
        }
    }



    public static void deleteFile(String name) {
        String sql = "DELETE FROM files WHERE name = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, Encryption.encrypt(name));

            stmt.executeUpdate();
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

            Path sourcePath = path;
            Path targetPath = Path.of(downloadPath + "/" + Decryption.decrypt(fileName));

            if (Files.exists(sourcePath)) {
                List<String> lines = Files.readAllLines(sourcePath);
                Files.createFile(targetPath);
                Files.write(targetPath, lines);

                if (Files.exists(targetPath)) {
                    System.out.println("\nExported File Successfully: " + targetPath);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void createFilesTable() {
        String query = "CREATE TABLE IF NOT EXISTS files (\n"
                + " name text NOT NULL,\n"
                + " category text, \n"
                + " size text NOT NULL,\n"
                + " path text NOT NULL,\n"
                + " custom text NOT NULL,\n"
                + " PRIMARY KEY (name, custom)\n"
                + ")";
        try {
            Statement stmt = getConnection().createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createUserTable() {
        String query = "CREATE TABLE IF NOT EXISTS users (" +
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
        createUserTable();
        createFilesTable();
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(SQLiteURL);
    }
}
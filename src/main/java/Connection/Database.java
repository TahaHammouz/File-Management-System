package Connection;

import Crypto.Decryption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.List;
import static Constants.Constants.*;

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
                                  String size, Path path, String custom) {

        String query = "INSERT INTO files(name, category, size, path, custom) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, String.valueOf(name));
            preparedStatement.setString(2, category);
            preparedStatement.setString(3, size);
            preparedStatement.setString(4, String.valueOf(path));
            preparedStatement.setString(5, custom);
            preparedStatement.executeUpdate();

            System.out.println("Row Inserted");
        } catch (SQLException var7) {
            System.out.println(var7.getMessage());
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

    public void createFilesTable() {
        String query = "CREATE TABLE IF NOT EXISTS files (\n"
                + " name text NOT NULL,\n"
                + " category text NOT NULL,\n"
                + " size text NOT NULL,\n"
                + " path text NOT NULL,\n"
                + " custom text NOT NULL,\n"
                + " PRIMARY KEY (name, custom)\n"
                + ")";
        try {
            Statement stmt = this.connection.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createUserTable() {
        String query = "CREATE TABLE IF NOT EXISTS users (" +
                "username text PRIMARY KEY NOT NULL, " +
                "password text NOT NULL, " +
                "role text NOT NULL)";

        try {
            Statement stmt = this.connection.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(SQLiteURL);
    }
}
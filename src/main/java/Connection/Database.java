package Connection;

import java.nio.file.Path;
import java.sql.*;

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
                                  String size, Path path,
                                  String custom) {
        String sql = "INSERT INTO files(name, category, size, path, custom) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(name));
            preparedStatement.setString(2, category);
            preparedStatement.setString(3, size);
            preparedStatement.setString(4, String.valueOf(path));
            preparedStatement.setString(5, custom);
            preparedStatement.executeUpdate();
            System.out.println("row inserted");

        } catch (SQLException var7) {
            System.out.println(var7.getMessage());
        }

    }

    public void createFilesTable() {
        // Create a table to store file information
        String files = "CREATE TABLE IF NOT EXISTS files (\n"
                + " name text NOT NULL,\n"
                + " category text NOT NULL,\n"
                + " size text NOT NULL,\n"
                + " path text NOT NULL,\n"
                + " custom text NOT NULL,\n"
                + " PRIMARY KEY (name, custom)\n"
                + ");";
        try {
            Statement stmt = this.connection.createStatement();
            stmt.execute(files);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void createUserTable() {

        String sql = "CREATE TABLE IF NOT EXISTS users (username text PRIMARY KEY NOT NULL ,password text NOT NULL,role text NOT NULL)";
        try {
            Statement stmt = this.connection.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(SQLiteURL);
    }


}
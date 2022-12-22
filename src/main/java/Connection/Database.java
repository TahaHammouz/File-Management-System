package Connection;

import java.nio.file.Path;
import java.sql.*;

import static Constants.Constants.URL;

public class Database {
    private  Connection connection;


    public Database() throws ClassNotFoundException {
        // Connect to the database

        try {
            Class.forName("org.sqlite.JDBC");

            this.connection = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createFilesTable() {
        // Create a table to store file information
        String files = "CREATE TABLE IF NOT EXISTS files (\n"
                + " name text NOT NULL,\n"
                + " category text NOT NULL,\n"
                + " size integer NOT NULL,\n"
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
        return DriverManager.getConnection(URL);
    }


}
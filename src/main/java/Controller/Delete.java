package Controller;
import FileReporsitory.FileRepository;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import static Connection.Database.getConnection;

public class Delete {
    public static void deleteFile() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter File Name (with extension) to Export:");
        String fileName = scanner.nextLine();
        System.out.println("What is the file size ? small, medium or large?:");
        String custom = scanner.nextLine();

        String query = "DELETE FROM Files WHERE name = ? AND custom = ?";

        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, fileName);
            preparedStatement.setString(2, custom);
            preparedStatement.executeUpdate();
            System.out.println("file is deleted");

            FileRepository.removeFile(new File(fileName));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

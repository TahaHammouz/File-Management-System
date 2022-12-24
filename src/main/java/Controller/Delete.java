package Controller;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static Connection.Database.getConnection;

public class Delete {
    public static void deleteFile(String name, String custom) throws SQLException {
        String query = "DELETE FROM Files WHERE name = ? AND custom = ?";

        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, custom);
            preparedStatement.executeUpdate();
            System.out.println("file is deleted");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

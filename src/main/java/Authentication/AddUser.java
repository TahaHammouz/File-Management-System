package Authentication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static Constants.Constants.*;

public class AddUser {
    public static void insertUser(String userName, String passWord, String role) {
        final String SQL = "INSERT INTO users VALUES(?,?,?)";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(SQL);) {
            ps.setString(1, userName);
            ps.setString(2, passWord);
            ps.setString(3, role);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(SQLiteURL);
    }
}

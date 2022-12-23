package Authentication;

import Exception.AuthException;
import Logger.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {
    public static User loginDatabase(String userName, String passWord) throws SQLException, AuthException {
        User user = null;
        Connection connection = null;
        connection = DriverManager.getConnection("jdbc:sqlite:FilesRepository.db");
        String query = "select * from users where Username=? and Password=?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, userName);
        stmt.setString(2, passWord);
        ResultSet res = stmt.executeQuery();
        if (res.next()) {
            String position = res.getString("role");
            user = new User(userName, passWord, position);
            return user;
        } else {
            Logger.logError("User Does Not Exist");
            throw new AuthException("User Does Not Exist");
        }
    }
}

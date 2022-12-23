import Authentication.Login;
import Authentication.User;
import  Exception.*;

import java.sql.SQLException;

import static Connection.Database.createUserTable;

public class Main {
    public static void main(String[] args) {
        createUserTable();
        try{
            User user = Login.loginDatabase("taha", "123");
            switch (user.role) {
                case "admin":

                    System.out.println("admin");
                    break;
                case "staff":

                    System.out.println("staff");
                    break;
                case "reader":
                    System.out.println("reader");
                    break;

            }
        }catch (AuthException e){
            System.err.print(e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
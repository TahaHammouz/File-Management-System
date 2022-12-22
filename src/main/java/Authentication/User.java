package Authentication;

public class User {

    public String userName;
    public String passWord;
    public String role;

    public User(String userName, String passWord, String position) {
        this.userName = userName;
        this.passWord = passWord;
        this.role = position;
    }
}

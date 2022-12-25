//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Authentication;

public class User {
    public String userName;
    public String passWord;
    public String role;

    public String getUserName() {
        return this.userName;
    }


    public String getPassWord() {
        return this.passWord;
    }


    public String getRole() {
        return this.role;
    }


    public User(String userName, String passWord, String position) {
        this.userName = userName;
        this.passWord = passWord;
        this.role = position;
    }
}

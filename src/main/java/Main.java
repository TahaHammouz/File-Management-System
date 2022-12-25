import Connection.ClasseficationFiles;
import Connection.Database;
import Controller.Post;

public class Main {

    public static void main(String[] args) throws Exception {
        Database.getConnection();
        Database.createFilesTable();
        Post.importFile();
        ClasseficationFiles.classifyFiles();
    }
}
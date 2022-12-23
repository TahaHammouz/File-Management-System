package Database;

public class SQLiteDB {
    private static SQLiteDB instance = null;
    private String url;
    private Boolean status;

    public  static  SQLiteDB getConnection(String url){
        System.out.println("getConnection .. ");// 12
        if (instance == null) {
            System.out.println("Wait in synchronized block");//4
            synchronized (SQLiteDB.class) {//stop here ...
                if (instance == null) {// not null
                    System.out.println("Establishing the db connection once!");//once
                    instance = new SQLiteDB(url);
                }
            }
        }
        return instance;
    }
    private SQLiteDB(String url) {
        status = false;
        this.url = url;
        connect(this.url);
    }

    public void connect(String url) {
        System.out.println("Connecting .... ");
        try {
            Thread.sleep(5000);//wait for 5 seconds
            this.status = true;
        } catch (Exception e){
            System.err.println(e);
            this.status = false;
        }
        System.out.println("Connected!");
    }

    public Boolean getStatus() {
        return status;
    }

    public Object executeQuery(String query){
        if (!this.status){
            connect(this.url);
        }
        //System.out.println("Executing query: " + query);
        return "This is the result";
    }
}
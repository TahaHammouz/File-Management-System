# File-Management-System

## Overview
Many companies use File-Management to manage their work, but there are some problems to deal with, such as Unencrypted files being stored, requests are not accepted, all requests for files being delayed, and the system is not scalable to increase user numbers and get detailed system logs.
With this File-Management, It is easy to give permissions to users, and your files are protected, and you can encrypt them while they are stored, so every action in the system will be logged. Your work will be easier if you have a full version control system for your files.


## Built-in Classes and Methods used
### Here are the built-in methods that are used in we project:

DriverManager is typically used to establish a connection to a database, getConnection method in the DriverManager class at is used to establish a connection to a database. It takes a URL as an argument and returns a Connection object that represents the connection to the database.
```java 
public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(SQLiteURL);
}
```

This Statement that loads the JDBC driver class for SQLite. The JDBC driver class provides a set of methods for connecting to a database. 
```java
Class.forName("org.sqlite.JDBC");
```

Represents a SQL statement. `execute` method in the `Statement` and `PreparedStatement` classes that are used to execute a SQL statement.
```java 
Statement stmt = getConnection().createStatement();
stmt.execute(query);
```

PreparedStatement used to execute the same or similar SQL statements multiple times with different parameter values. ResultSet represents the results of a SQL query. It is used to retrieve and process the rows of the results returned by a `Statement` or `PreparedStatement`.
```java 
PreparedStatement preparedStatement = getConnection().prepareStatement(query);
preparedStatement.setString(1, fileName);
preparedStatement.setString(2, custom);

ResultSet rs = preparedStatement.executeQuery();
```

- ```java executeQuery``` method in the `Statement` and `PreparedStatement` classes that are used to execute a SQL SELECT statement only and retrieve the results of the query.

Method in the `Statement` and `PreparedStatement` classes that are used to execute a SQL statement.
```java 
try (
        Connection con = getConnection(); 
        PreparedStatement ps = con.prepareStatement(SQL)
) {
    ps.setString(1, userName);
    ps.setString(2, passWord);
    ps.setString(3, role);

    ps.executeUpdate();
}
```

`Path` class represents a file or directory path. It is used to manipulate file and directory paths and perform operations on what they represent. `File` class provides various static methods for performing operations on files and directories.
```java path = Path.of(Objects.requireNonNull(file_path()));
File file = new File(path.toUri());
FileRepository.addFile(file);
try {
    if (Files.exists(path)) {
        Database.insertFile(Encryption.encrypt(String.valueOf(path.getFileName())), file_type(),
                file_category(), file_size(), path, file_custom());
    }
}```


```java 
public static String encrypt(String fileName) throws Exception {
    MessageDigest digest = MessageDigest.getInstance(KEY_DERIVATION_ALGORITHM);
    byte[] key = digest.digest(PASSWORD.getBytes());
    key = Arrays.copyOf(key, KEY_LENGTH);
    Cipher cipher = Cipher.getInstance(TRANSFORMATION);
    SecretKeySpec keySpec = new SecretKeySpec(key, ALGORITHM);
    cipher.init(1, keySpec);
    byte[] encryptedFileName = cipher.doFinal(fileName.getBytes());
    return Base64.getEncoder().encodeToString(encryptedFileName);
}
```

## Use Case Diagram
<img width="2850" alt="Untitled (1)" src="https://user-images.githubusercontent.com/68341943/209454593-feefe646-c341-4297-84c4-202611d810cb.png">

## Class Diagram
<img width="4149" alt="Untitled (2)" src="https://user-images.githubusercontent.com/68341943/209506331-a7cc3441-6b17-494b-adf1-a524de69b6a7.png">

## C4 Diagram
<img width="9107" alt="C4 model - File Management System (4)" src="https://user-images.githubusercontent.com/68341943/209506497-c7754097-6de8-41be-9cd5-b7f3986897c6.png">

## Contributors
Abed Kharoubi

Taha Hamouz

Malak Mousa


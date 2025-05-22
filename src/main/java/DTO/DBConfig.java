package DTO;

public class DBConfig {
    public String url;
    public String username;
    public String password;
    public String driver;

    public DBConfig(String url, String user, String pass, String driver) {
        this.url      = url;
        this.username = user;
        this.password = pass;
        this.driver   = driver;
    }
}

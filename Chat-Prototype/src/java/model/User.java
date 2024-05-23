package model;

public class User {
    private String userUID;
    private String username;
    private String password;
    private String email;
    private String salt;

    public User() {
    }

    public User(String userUID, String username, String password, String email, String salt) {
        this.userUID = userUID;
        this.username = username;
        this.password = password;
        this.email = email;
        this.salt = salt;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public String toString() {
        return "User{" + "userUID=" + userUID + ", username=" + username + ", password=" + password + ", email=" + email + ", salt=" + salt + '}';
    }
    
    
}

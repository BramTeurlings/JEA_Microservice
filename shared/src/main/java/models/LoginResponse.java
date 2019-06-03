package models;

public class LoginResponse {

    public String token;
    public String username;

    public LoginResponse(){

    }

    public LoginResponse(String token, String user) {
        this.token = token;
        this.username = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

package fr.viper.core.login;

public class LoginRequest {
    private final String id;
    private final String password;

    public LoginRequest(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }
}

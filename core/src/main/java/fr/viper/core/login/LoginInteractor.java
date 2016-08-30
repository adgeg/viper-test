package fr.viper.core.login;

public interface LoginInteractor {
    void login(String name, String password);

    void login(LoginRequest request);
}

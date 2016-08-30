package fr.viper.core.login;

public interface LoginPresenter {
    void displayEmptyUserName();

    void displayEmptyPassword();

    void displayLoading();

    void displayUnknownName();

    void displayInvalidPassword();

    void displaySuccessfulLogin(LoginResponse response);
}

package fr.viper.app.login;

import fr.viper.core.entities.User;

public interface LoginView {
    void displayEmptyPassword();

    void displayEmptyUserName();

    void displayLoading();

    void displayUnknownName();

    void displayInvalidPassword();

    void displaySuccessfulLogin(User user);
}

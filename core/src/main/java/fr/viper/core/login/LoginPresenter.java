package fr.viper.core.login;

import fr.viper.core.entities.User;

public interface LoginPresenter {
    void displayEmptyUserName();

    void displayEmptyPassword();

    void displayLoading();

    void displayUnknownName();

    void displayInvalidPassword();

    void displayLoggedUser(User user);
}

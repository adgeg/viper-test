package fr.viper.core.login;

import fr.viper.core.entities.User;

public interface LoginPresenter {
    void displayEmptyId();

    void displayEmptyPassword();

    void displayLoading();

    void displayUnknownId();

    void displayInvalidPassword();

    void displayLoggedUser(User user);
}

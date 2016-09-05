package fr.viper.core.login;

import fr.viper.core.entities.User;

public interface LoginPresenter {
    void presentEmptyId();

    void presentEmptyPassword();

    void presentPendingRequest();

    void presentUnknownId();

    void presentInvalidPassword();

    void presentLoggedUser(User user);
}

package fr.viper.core.login;

import fr.viper.core.entities.User;

public interface LoginOutputPort {
    void onEmptyId();

    void onEmptyPassword();

    void onPendingRequest();

    void onUnknownId();

    void onInvalidPassword();

    void onLoggedUser(User user);
}

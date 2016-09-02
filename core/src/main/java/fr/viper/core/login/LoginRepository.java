package fr.viper.core.login;

import fr.viper.core.entities.User;

public interface LoginRepository {
    User getUser(LoginRequest request) throws UnknownUserException, InvalidPasswordException;

    class UnknownUserException extends Exception {
    }

    class InvalidPasswordException extends Exception {
    }
}

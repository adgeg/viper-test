package fr.viper.core.login;

public interface LoginGateway {
    String checkCredentials(LoginRequest request) throws UnknownUserException, InvalidPasswordException;

    class UnknownUserException extends Exception {
    }

    class InvalidPasswordException extends Exception {
    }
}

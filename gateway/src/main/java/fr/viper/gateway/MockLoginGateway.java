package fr.viper.gateway;

import fr.viper.core.login.LoginGateway;

import fr.viper.core.login.LoginRequest;

public class MockLoginGateway implements LoginGateway {
    @Override
    public String checkCredentials(LoginRequest request) throws UnknownUserException, InvalidPasswordException {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "You are logged in";
    }
}

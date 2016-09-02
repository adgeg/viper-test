package fr.viper.gateway;

import fr.viper.core.entities.User;
import fr.viper.core.login.LoginRepository;
import fr.viper.core.login.LoginRequest;

public class MockLoginRepository implements LoginRepository {
    @Override
    public User getUser(LoginRequest request) throws UnknownUserException, InvalidPasswordException {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new User() {

        };
    }
}

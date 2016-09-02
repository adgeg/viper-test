package fr.viper.gateway;

import java.util.Date;

import fr.viper.core.entities.User;
import fr.viper.core.login.LoginRepository;
import fr.viper.core.login.LoginRequest;

public class MockLoginRepository implements LoginRepository {
    @Override
    public User getUser(final LoginRequest request) throws UnknownUserException, InvalidPasswordException {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new User() {

            @Override
            public String getFirstName() {
                return "M.";
            }

            @Override
            public String getLastName() {
                return request.getName();
            }

            @Override
            public Date getLastLogin() {
                return new Date();
            }
        };
    }
}

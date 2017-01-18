package fr.viper.repositories.login;

import fr.viper.core.entities.User;
import fr.viper.core.login.LoginRepository;
import fr.viper.core.login.LoginRequest;

public class SimulateDelayLoginRepository implements LoginRepository {
    private static final int DELAY = 2000;
    private final LoginRepository repository;

    public SimulateDelayLoginRepository(LoginRepository repository) {
        this.repository = repository;
    }

    @Override
    public User getUser(LoginRequest request) throws UnknownUserException, InvalidPasswordException {
        simulateDelay();
        return repository.getUser(request);
    }

    protected void simulateDelay() {
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

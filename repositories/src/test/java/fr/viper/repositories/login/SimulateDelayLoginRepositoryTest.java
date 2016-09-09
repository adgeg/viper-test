package fr.viper.repositories.login;

import org.junit.Test;

import fr.viper.core.login.LoginRepository;
import fr.viper.core.login.LoginRequest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SimulateDelayLoginRepositoryTest {
    @Test
    public void getUser_ShouldTriggerMethodOnDecoratedRepository() throws LoginRepository.InvalidPasswordException, LoginRepository.UnknownUserException {
        final LoginRequest loginRequest = new LoginRequest("id", "password");
        final LoginRepository decorated = mock(LoginRepository.class);
        final SimulateDelayLoginRepository repository = new SimulateDelayLoginRepository(decorated) {
            @Override
            protected void simulateDelay() {

            }
        };
        repository.getUser(loginRequest);
        verify(decorated).getUser(loginRequest);
    }
}
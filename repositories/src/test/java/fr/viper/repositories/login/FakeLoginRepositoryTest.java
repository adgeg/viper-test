package fr.viper.repositories.login;

import org.junit.Before;
import org.junit.Test;

import fr.viper.core.entities.User;
import fr.viper.core.login.LoginRequest;
import fr.viper.repositories.MapperModule;

import static fr.viper.core.login.LoginRepository.InvalidPasswordException;
import static fr.viper.core.login.LoginRepository.UnknownUserException;
import static org.assertj.core.api.Assertions.assertThat;

public class FakeLoginRepositoryTest {
    private FakeLoginRepository repository;

    @Before
    public void setUp() throws Exception {
        repository = new FakeLoginRepository(new MapperModule().getObjectMapper());
    }

    @Test(expected = UnknownUserException.class)
    public void getUser_ShouldThrowUnknownUserException() throws InvalidPasswordException, UnknownUserException {
        final LoginRequest request = new LoginRequest("unknown", "pwd");
        repository.getUser(request);
    }

    @Test(expected = InvalidPasswordException.class)
    public void getUser_ShouldThrowInvalidPasswordException() throws InvalidPasswordException, UnknownUserException {
        final LoginRequest request = new LoginRequest("name", "invalid");
        repository.getUser(request);
    }

    @Test
    public void getUser() throws InvalidPasswordException, UnknownUserException {
        final LoginRequest request = new LoginRequest("name", "pwd");
        final User user = repository.getUser(request);
        assertThat(user.getFirstName()).isEqualTo("Louis");
        assertThat(user.getLastName()).isEqualTo("CK");
        assertThat(user.getLastLogin()).isEqualTo("2016-09-01T12:35:14");
    }
}
package fr.viper.core.login;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.bechte.junit.runners.context.HierarchicalContextRunner;
import fr.viper.core.entities.User;

import static fr.viper.core.login.LoginRepository.InvalidPasswordException;
import static fr.viper.core.login.LoginRepository.UnknownUserException;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(HierarchicalContextRunner.class)
public class LoginInteractorTest {
    @Mock private LoginRepository repository;
    @Mock private LoginPresenter presenter;
    @InjectMocks private LoginInteractor interactor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    public class InvalidInputs {
        @Test
        public void login_WithEmptyId() {
            final LoginRequest request = new LoginRequest("", "");
            interactor.login(request);
            verify(presenter).presentEmptyId();
        }

        @Test
        public void login_WithEmptyPassword() {
            final LoginRequest request = new LoginRequest("id", "");
            interactor.login(request);
            verify(presenter).presentEmptyPassword();
        }

    }

    public class LoginFailures {
        @Test
        public void login_WhenIdIsUnknown() throws Exception {
            final LoginRequest request = new LoginRequest("id", "password");
            doThrow(UnknownUserException.class).when(repository).getUser(request);
            interactor.login(request);
            verify(presenter).presentPendingRequest();
            verify(presenter).presentUnknownId();
        }

        @Test
        public void login_WhenPasswordIsInvalid() throws Exception {
            final LoginRequest request = new LoginRequest("id", "password");
            doThrow(InvalidPasswordException.class).when(repository).getUser(request);
            interactor.login(request);
            verify(presenter).presentPendingRequest();
            verify(presenter).presentInvalidPassword();
        }
    }

    public class LoginSuccess {
        @Test
        public void successfulLogin() throws Exception {
            final LoginRequest request = new LoginRequest("id", "password");
            final User user = mock(User.class);
            given(repository.getUser(request)).willReturn(user);
            interactor.login(request);
            verify(presenter).presentPendingRequest();
            verify(presenter).presentLoggedUser(user);
        }
    }
}
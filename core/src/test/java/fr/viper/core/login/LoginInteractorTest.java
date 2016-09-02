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
    @Mock private LoginRepository gateway;
    @Mock private LoginPresenter presenter;
    @InjectMocks private LoginInteractor interactor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    public class InvalidInputs {
        @Test
        public void login_WithEmptyName() {
            final LoginRequest request = new LoginRequest("", "");
            interactor.login(request);
            verify(presenter).displayEmptyUserName();
        }

        @Test
        public void login_WithEmptyPassword() {
            final LoginRequest request = new LoginRequest("name", "");
            interactor.login(request);
            verify(presenter).displayEmptyPassword();
        }

    }

    public class LoginFailures {
        @Test
        public void login_WhenNameIsUnknown() throws Exception {
            final LoginRequest request = new LoginRequest("name", "password");
            doThrow(UnknownUserException.class).when(gateway).getUser(request);
            interactor.login(request);
            verify(presenter).displayLoading();
            verify(presenter).displayUnknownName();
        }

        @Test
        public void login_WhenPasswordIsInvalid() throws Exception {
            final LoginRequest request = new LoginRequest("name", "password");
            doThrow(InvalidPasswordException.class).when(gateway).getUser(request);
            interactor.login(request);
            verify(presenter).displayLoading();
            verify(presenter).displayInvalidPassword();
        }
    }

    public class LoginSuccess {
        @Before
        public void setup() {
            MockitoAnnotations.initMocks(this);
        }

        @Test
        public void successfulLogin_GatewayMessage() throws Exception {
            final LoginRequest request = new LoginRequest("name", "password");
            final User user = mock(User.class);
            given(gateway.getUser(request)).willReturn(user);
            interactor.login(request);
            verify(presenter).displayLoading();
            verify(presenter).displayLoggedUser(user);
        }
    }
}
package fr.viper.core.login;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.bechte.junit.runners.context.HierarchicalContextRunner;

import static fr.viper.core.login.LoginGateway.InvalidPasswordException;
import static fr.viper.core.login.LoginGateway.UnknownUserException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@RunWith(HierarchicalContextRunner.class)
public class LoginInteractorTest {
    @Mock private LoginGateway gateway;
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
            doThrow(UnknownUserException.class).when(gateway).checkCredentials(request);
            interactor.login(request);
            verify(presenter).displayLoading();
            verify(presenter).displayUnknownName();
        }

        @Test
        public void login_WhenPasswordIsInvalid() throws Exception {
            final LoginRequest request = new LoginRequest("name", "password");
            doThrow(InvalidPasswordException.class).when(gateway).checkCredentials(request);
            interactor.login(request);
            verify(presenter).displayLoading();
            verify(presenter).displayInvalidPassword();
        }

    }

    public class LoginSuccess {
        @Captor private ArgumentCaptor<LoginResponse> captor;

        @Before
        public void setup() {
            MockitoAnnotations.initMocks(this);
        }

        @Test
        public void successfulLogin_GatewayMessage() throws Exception {
            final LoginRequest request = new LoginRequest("name", "password");
            given(gateway.checkCredentials(request)).willReturn("message");
            interactor.login(request);
            verify(presenter).displayLoading();
            verify(presenter).displaySuccessfulLogin(captor.capture());
            assertThat(captor.getValue().getMessage()).isEqualTo("message");
        }

        @Test
        public void successfulLogin_OtherGatewayMessage() throws Exception {
            final LoginRequest request = new LoginRequest("name", "password");
            given(gateway.checkCredentials(request)).willReturn("other-message");
            interactor.login(request);
            verify(presenter).displayLoading();
            verify(presenter).displaySuccessfulLogin(captor.capture());
            assertThat(captor.getValue().getMessage()).isEqualTo("other-message");
        }

    }
}
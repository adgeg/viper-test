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
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@RunWith(HierarchicalContextRunner.class)
public class LoginInteractorImplTest {
    @Mock private fr.viper.core.login.LoginGateway gateway;
    @Mock private LoginPresenter presenter;
    @InjectMocks private LoginInteractorImpl interactor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    public class LoginWithParameters {
        @Captor private ArgumentCaptor<fr.viper.core.login.LoginRequest> captor;

        @Before
        public void setup() {
            MockitoAnnotations.initMocks(this);
            interactor = spy(interactor);
        }

        @Test
        public void login_ShouldCreateRequest() {
            interactor.login("name", "password");
            verify(interactor).login(captor.capture());
            assertThat(captor.getValue().getName()).isEqualTo("name");
            assertThat(captor.getValue().getPassword()).isEqualTo("password");
        }
    }

    public class InvalidInputs {
        @Test
        public void login_WithEmptyName() {
            final fr.viper.core.login.LoginRequest request = new fr.viper.core.login.LoginRequest("", "");
            interactor.login(request);
            verify(presenter).displayEmptyUserName();
        }

        @Test
        public void login_WithEmptyPassword() {
            final fr.viper.core.login.LoginRequest request = new fr.viper.core.login.LoginRequest("name", "");
            interactor.login(request);
            verify(presenter).displayEmptyPassword();
        }

    }

    public class LoginFailures {
        @Test
        public void login_WhenNameIsUnknown() throws Exception {
            final fr.viper.core.login.LoginRequest request = new fr.viper.core.login.LoginRequest("name", "password");
            doThrow(UnknownUserException.class).when(gateway).checkCredentials(request);
            interactor.login(request);
            verify(presenter).displayLoading();
            verify(presenter).displayUnknownName();
        }

        @Test
        public void login_WhenPasswordIsInvalid() throws Exception {
            final fr.viper.core.login.LoginRequest request = new fr.viper.core.login.LoginRequest("name", "password");
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
            final fr.viper.core.login.LoginRequest request = new fr.viper.core.login.LoginRequest("name", "password");
            given(gateway.checkCredentials(request)).willReturn("message");
            interactor.login(request);
            verify(presenter).displayLoading();
            verify(presenter).displaySuccessfulLogin(captor.capture());
            assertThat(captor.getValue().getMessage()).isEqualTo("message");
        }

        @Test
        public void successfulLogin_OtherGatewayMessage() throws Exception {
            final fr.viper.core.login.LoginRequest request = new LoginRequest("name", "password");
            given(gateway.checkCredentials(request)).willReturn("other-message");
            interactor.login(request);
            verify(presenter).displayLoading();
            verify(presenter).displaySuccessfulLogin(captor.capture());
            assertThat(captor.getValue().getMessage()).isEqualTo("other-message");
        }

    }
}
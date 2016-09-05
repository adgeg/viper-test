package fr.viper.app.login.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import fr.viper.core.login.LoginInteractor;
import fr.viper.core.login.LoginRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

public class LoginControllerTest {
    @Captor private ArgumentCaptor<LoginRequest> captor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void executeRequest() {
        final LoginInteractor interactor = Mockito.mock(LoginInteractor.class);
        final LoginController controller = new LoginControllerImpl(interactor);
        controller.executeRequest("name", "password");
        verify(interactor).login(captor.capture());
        assertThat(captor.getValue().getId()).isEqualTo("name");
        assertThat(captor.getValue().getPassword()).isEqualTo("password");
    }
}

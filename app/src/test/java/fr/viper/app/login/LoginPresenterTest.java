package fr.viper.app.login;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import fr.viper.core.login.LoginResponse;

import static org.mockito.Mockito.*;

public class LoginPresenterTest {
    @Rule public MockitoRule rule = MockitoJUnit.rule();

    @Mock private LoginView view;
    @InjectMocks private LoginPresenterImpl presenter;

    @Test
    public void displayEmptyUserName() {
        presenter.displayEmptyUserName();
        verify(view).displayEmptyUserName();
    }

    @Test
    public void displayEmptyPassword() {
        presenter.displayEmptyPassword();
        verify(view).displayEmptyPassword();
    }

    @Test
    public void displayLoading() {
        presenter.displayLoading();
        verify(view).displayLoading();
    }

    @Test
    public void displayUnknownName() {
        presenter.displayUnknownName();
        verify(view).displayUnknownName();
    }

    @Test
    public void displayInvalidPassword() {
        presenter.displayInvalidPassword();
        verify(view).displayInvalidPassword();
    }

    @Test
    public void displaySuccessfulLogin() {
        final LoginResponse response = new LoginResponse("message");
        presenter.displaySuccessfulLogin(response);
        verify(view).displaySuccessfulLogin("message");
    }
}
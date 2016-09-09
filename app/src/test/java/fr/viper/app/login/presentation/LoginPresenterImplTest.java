package fr.viper.app.login.presentation;

import android.support.annotation.StringRes;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Date;
import java.util.GregorianCalendar;

import fr.viper.app.BuildConfig;
import fr.viper.app.R;
import fr.viper.core.entities.User;

import static fr.viper.app.login.presentation.LoginViewModel.DISPLAY_FORM;
import static fr.viper.app.login.presentation.LoginViewModel.DISPLAY_LOADING;
import static fr.viper.app.login.presentation.LoginViewModel.DISPLAY_SUCCESS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class LoginPresenterImplTest {
    @Rule public MockitoRule rule = MockitoJUnit.rule();

    @Mock private LoginView view;
    private LoginPresenterImpl presenter;

    @Before
    public void setup() {
        presenter = new LoginPresenterImpl(view, RuntimeEnvironment.application);
    }

    @Test
    public void presentEmptyId() {
        presenter.presentEmptyId();
        assertErrorMessage(R.string.empty_id);
    }

    @Test
    public void presentEmptyPassword() {
        presenter.presentEmptyPassword();
        assertErrorMessage(R.string.empty_password);
    }

    @Test
    public void presentPendingRequest() {
        presenter.presentPendingRequest();
        assertShouldHideKeyBoard();
        assertDisplayedChild(DISPLAY_LOADING);
    }

    @Test
    public void presentUnknownId() {
        presenter.presentUnknownId();
        assertErrorMessage(R.string.unknown_id);
        assertDisplayedChild(DISPLAY_FORM);
    }

    @Test
    public void presentInvalidPassword() {
        presenter.presentInvalidPassword();
        assertErrorMessage(R.string.invalid_password);
        assertDisplayedChild(DISPLAY_FORM);
    }

    @Test
    public void presentLoggedUser_ShouldDisplayHelloToUser() {
        final User user = mockUser("Louis", "CK");
        presenter.presentLoggedUser(user);
        assertTitle("Bienvenue Louis CK");
        assertDisplayedChild(DISPLAY_SUCCESS);
    }

    @Test
    public void presentLoggedUser_ShouldDisplayLastUserLoginDate() {
        final User user = mockUser(2016, 8, 1);
        presenter.presentLoggedUser(user);
        assertDescription("Dernière connexion le 1 septembre 2016");
        assertDisplayedChild(DISPLAY_SUCCESS);
    }

    private User mockUser(String firstName, String lastName) {
        final User user = mock(User.class);
        given(user.getFirstName()).willReturn(firstName);
        given(user.getLastName()).willReturn(lastName);
        given(user.getLastLogin()).willReturn(new Date());
        return user;
    }

    private User mockUser(int year, int month, int date) {
        final User user = mock(User.class);
        final GregorianCalendar calendar = new GregorianCalendar(year, month, date);
        given(user.getLastLogin()).willReturn(calendar.getTime());
        return user;
    }

    private void assertErrorMessage(@StringRes int expected) {
        final ArgumentCaptor<LoginViewModel> captor = ArgumentCaptor.forClass(LoginViewModel.class);
        verify(view).displayViewModel(captor.capture());
        assertThat(captor.getValue().errorResId).isEqualTo(expected);
    }

    private void assertTitle(String expected) {
        final ArgumentCaptor<LoginViewModel> captor = ArgumentCaptor.forClass(LoginViewModel.class);
        verify(view).displayViewModel(captor.capture());
        assertThat(captor.getValue().title).isEqualTo(expected);
    }

    private void assertDescription(String expected) {
        final ArgumentCaptor<LoginViewModel> captor = ArgumentCaptor.forClass(LoginViewModel.class);
        verify(view).displayViewModel(captor.capture());
        assertThat(captor.getValue().description).isEqualTo(expected);
    }

    private void assertDisplayedChild(int expected) {
        final ArgumentCaptor<LoginViewModel> captor = ArgumentCaptor.forClass(LoginViewModel.class);
        verify(view).displayViewModel(captor.capture());
        assertThat(captor.getValue().displayedChild).isEqualTo(expected);
    }

    private void assertShouldHideKeyBoard() {
        final ArgumentCaptor<LoginViewModel> captor = ArgumentCaptor.forClass(LoginViewModel.class);
        verify(view).displayViewModel(captor.capture());
        assertThat(captor.getValue().shouldHideKeyboard).isTrue();
    }
}
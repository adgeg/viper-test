package fr.viper.app.login;

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
        verify(view).displayErrorMessage(R.string.empty_id);
    }

    @Test
    public void presentEmptyPassword() {
        presenter.presentEmptyPassword();
        verify(view).displayErrorMessage(R.string.empty_password);
    }

    @Test
    public void presentPendingRequest() {
        presenter.presentPendingRequest();
        verify(view).displayLoading();
    }

    @Test
    public void presentUnknownId() {
        presenter.presentUnknownId();
        verify(view).displayErrorMessage(R.string.unknown_id);
    }

    @Test
    public void presentInvalidPassword() {
        presenter.presentInvalidPassword();
        verify(view).displayErrorMessage(R.string.invalid_password);
    }

    @Test
    public void presentLoggedUser_ShouldDisplayHelloToUser() {
        final User user = mockUser("Louis", "CK");
        presenter.presentLoggedUser(user);
        assertViewModelTitle("Bienvenue Louis CK");
    }

    @Test
    public void presentLoggedUser_ShouldDisplayLastUserLoginDate() {
        final User user = mockUser(2016, 8, 1);
        presenter.presentLoggedUser(user);
        assertViewModelDescription("Dernière connexion le 1 septembre 2016");
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

    private void assertViewModelTitle(String expected) {
        final ArgumentCaptor<UserViewModel> captor = ArgumentCaptor.forClass(UserViewModel.class);
        verify(view).displaySuccessfulLogin(captor.capture());
        assertThat(captor.getValue().getTitle()).isEqualTo(expected);
    }

    private void assertViewModelDescription(String expected) {
        final ArgumentCaptor<UserViewModel> captor = ArgumentCaptor.forClass(UserViewModel.class);
        verify(view).displaySuccessfulLogin(captor.capture());
        assertThat(captor.getValue().getDescription()).isEqualTo(expected);
    }
}
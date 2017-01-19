package fr.viper.login.presentation;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Date;
import java.util.GregorianCalendar;

import fr.viper.core.entities.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class LoginPresenterTest {
    @Rule public MockitoRule rule = MockitoJUnit.rule();

    @Mock private LoginView view;
    @Captor private ArgumentCaptor<LoginViewModel> captor;
    private LoginPresenter presenter;

    @Before
    public void setup() {
        presenter = new LoginPresenter(view, RuntimeEnvironment.application);
    }

    @Test
    public void onEmptyId() {
        presenter.onEmptyId();
        verify(view).displayViewModel(captor.capture());
        assertThat(captor.getValue().error).isEqualTo("Identifiant vide");
    }

    @Test
    public void onEmptyPassword() {
        presenter.onEmptyPassword();
        verify(view).displayViewModel(captor.capture());
        assertThat(captor.getValue().error).isEqualTo("Mot de passe vide");
    }

    @Test
    public void onPendingRequest() {
        presenter.onPendingRequest();
        verify(view).displayViewModel(captor.capture());
        assertThat(captor.getValue().shouldDisplayLoading).isTrue();
    }

    @Test
    public void onUnknownId() {
        presenter.onUnknownId();
        verify(view).displayViewModel(captor.capture());
        assertThat(captor.getValue().error).isEqualTo("Identifiant inconnu");
        assertThat(captor.getValue().shouldDisplayForm).isTrue();
    }

    @Test
    public void onInvalidPassword() {
        presenter.onInvalidPassword();
        verify(view).displayViewModel(captor.capture());
        assertThat(captor.getValue().error).isEqualTo("Mot de passe incorrect");
        assertThat(captor.getValue().shouldDisplayForm).isTrue();
    }

    @Test
    public void onLoggedUser_ShouldDisplayHelloToUser() {
        final User user = mockUser("Louis", "CK");
        presenter.onLoggedUser(user);
        verify(view).displayViewModel(captor.capture());
        assertThat(captor.getValue().title).isEqualTo("Bienvenue Louis CK");
        assertThat(captor.getValue().shouldDisplayLoggedUser).isTrue();
    }

    @Test
    public void onLoggedUser_ShouldDisplayLastUserLoginDate() {
        final User user = mockUserWithLastLogin(2016, 8, 1);
        presenter.onLoggedUser(user);
        verify(view).displayViewModel(captor.capture());
        assertThat(captor.getValue().description).isEqualTo("Dernière connexion le 1 septembre 2016");
        assertThat(captor.getValue().shouldDisplayLoggedUser).isTrue();
    }

    private User mockUser(String firstName, String lastName) {
        final User user = mock(User.class);
        given(user.getFirstName()).willReturn(firstName);
        given(user.getLastName()).willReturn(lastName);
        given(user.getLastLogin()).willReturn(new Date());
        return user;
    }

    private User mockUserWithLastLogin(int year, int month, int date) {
        final User user = mock(User.class);
        final GregorianCalendar calendar = new GregorianCalendar(year, month, date);
        given(user.getLastLogin()).willReturn(calendar.getTime());
        return user;
    }
}
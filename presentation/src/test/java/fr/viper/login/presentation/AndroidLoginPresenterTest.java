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

import static fr.viper.login.presentation.LoginViewModel.DISPLAY_FORM;
import static fr.viper.login.presentation.LoginViewModel.DISPLAY_LOADING;
import static fr.viper.login.presentation.LoginViewModel.DISPLAY_SUCCESS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class AndroidLoginPresenterTest {
    @Rule public MockitoRule rule = MockitoJUnit.rule();

    @Mock private LoginView view;
    @Captor private ArgumentCaptor<LoginViewModel> captor;
    private AndroidLoginPresenter presenter;

    @Before
    public void setup() {
        presenter = new AndroidLoginPresenter(view, RuntimeEnvironment.application);
    }

    @Test
    public void presentEmptyId() {
        presenter.presentEmptyId();
        verify(view).displayViewModel(captor.capture());
        assertThat(captor.getValue().errorResId).isEqualTo(R.string.empty_id);
    }

    @Test
    public void presentEmptyPassword() {
        presenter.presentEmptyPassword();
        verify(view).displayViewModel(captor.capture());
        assertThat(captor.getValue().errorResId).isEqualTo(R.string.empty_password);
    }

    @Test
    public void presentPendingRequest() {
        presenter.presentPendingRequest();
        verify(view).displayViewModel(captor.capture());
        assertThat(captor.getValue().shouldHideKeyboard).isTrue();
        assertThat(captor.getValue().displayedChild).isEqualTo(DISPLAY_LOADING);
    }

    @Test
    public void presentUnknownId() {
        presenter.presentUnknownId();
        verify(view).displayViewModel(captor.capture());
        assertThat(captor.getValue().errorResId).isEqualTo(R.string.unknown_id);
        assertThat(captor.getValue().displayedChild).isEqualTo(DISPLAY_FORM);
    }

    @Test
    public void presentInvalidPassword() {
        presenter.presentInvalidPassword();
        verify(view).displayViewModel(captor.capture());
        assertThat(captor.getValue().errorResId).isEqualTo(R.string.invalid_password);
        assertThat(captor.getValue().displayedChild).isEqualTo(DISPLAY_FORM);
    }

    @Test
    public void presentLoggedUser_ShouldDisplayHelloToUser() {
        final User user = mockUser("Louis", "CK");
        presenter.presentLoggedUser(user);
        verify(view).displayViewModel(captor.capture());
        assertThat(captor.getValue().title).isEqualTo("Bienvenue Louis CK");
        assertThat(captor.getValue().displayedChild).isEqualTo(DISPLAY_SUCCESS);
    }

    @Test
    public void presentLoggedUser_ShouldDisplayLastUserLoginDate() {
        final User user = mockUserWithLastLogin(2016, 8, 1);
        presenter.presentLoggedUser(user);
        verify(view).displayViewModel(captor.capture());
        assertThat(captor.getValue().description).isEqualTo("Dernière connexion le 1 septembre 2016");
        assertThat(captor.getValue().displayedChild).isEqualTo(DISPLAY_SUCCESS);
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
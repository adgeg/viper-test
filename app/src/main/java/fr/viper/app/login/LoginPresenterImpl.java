package fr.viper.app.login;

import fr.viper.core.login.LoginPresenter;
import fr.viper.core.login.LoginResponse;

public class LoginPresenterImpl implements LoginPresenter {
    private final LoginView view;

    public LoginPresenterImpl(LoginView view) {
        this.view = view;
    }

    @Override
    public void displayEmptyUserName() {
        view.displayEmptyUserName();
    }

    @Override
    public void displayEmptyPassword() {
        view.displayEmptyPassword();
    }

    @Override
    public void displayLoading() {
        view.displayLoading();
    }

    @Override
    public void displayUnknownName() {
        view.displayUnknownName();
    }

    @Override
    public void displayInvalidPassword() {
        view.displayInvalidPassword();
    }

    @Override
    public void displaySuccessfulLogin(LoginResponse response) {
        view.displaySuccessfulLogin(response.getMessage());
    }
}

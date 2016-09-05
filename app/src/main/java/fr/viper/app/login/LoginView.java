package fr.viper.app.login;

import android.support.annotation.StringRes;

public interface LoginView {
    void displayLoading();

    void displayErrorMessage(@StringRes int messageResId);

    void displaySuccessfulLogin(UserViewModel viewModel);
}

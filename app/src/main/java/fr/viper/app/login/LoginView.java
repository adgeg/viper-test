package fr.viper.app.login;

public interface LoginView {
    void displayEmptyPassword();

    void displayEmptyId();

    void displayLoading();

    void displayUnknownId();

    void displayInvalidPassword();

    void displaySuccessfulLogin(UserViewModel viewModel);
}

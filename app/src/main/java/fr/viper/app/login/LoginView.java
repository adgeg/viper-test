package fr.viper.app.login;

public interface LoginView {
    void displayEmptyPassword();

    void displayEmptyUserName();

    void displayLoading();

    void displayUnknownName();

    void displayInvalidPassword();

    void displaySuccessfulLogin(UserViewModel viewModel);
}

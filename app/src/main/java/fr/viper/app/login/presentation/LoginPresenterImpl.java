package fr.viper.app.login.presentation;

import android.content.Context;
import android.text.format.DateUtils;

import fr.viper.app.R;
import fr.viper.core.entities.User;
import fr.viper.core.login.LoginPresenter;

import static android.text.format.DateUtils.formatDateTime;

public class LoginPresenterImpl implements LoginPresenter {
    private final LoginView view;
    private final Context context;

    public LoginPresenterImpl(LoginView view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void presentEmptyId() {
        view.displayErrorMessage(R.string.empty_id);
    }

    @Override
    public void presentEmptyPassword() {
        view.displayErrorMessage(R.string.empty_password);
    }

    @Override
    public void presentPendingRequest() {
        view.displayLoading();
    }

    @Override
    public void presentUnknownId() {
        view.displayErrorMessage(R.string.unknown_id);
    }

    @Override
    public void presentInvalidPassword() {
        view.displayErrorMessage(R.string.invalid_password);
    }

    @Override
    public void presentLoggedUser(User user) {
        final String title = getViewModelTitle(user);
        final String description = getViewModelDescription(user);
        final UserViewModel viewModel = new UserViewModel(title, description);
        view.displaySuccessfulLogin(viewModel);
    }

    private String getViewModelDescription(User user) {
        final String lastLoginFormatted = formatDateTime(context, user.getLastLogin().getTime(), DateUtils.FORMAT_ABBREV_WEEKDAY);
        return context.getString(R.string.last_login, lastLoginFormatted);
    }

    private String getViewModelTitle(User user) {
        String userName = user.getFirstName() + " " + user.getLastName();
        return context.getString(R.string.welcome, userName);
    }
}

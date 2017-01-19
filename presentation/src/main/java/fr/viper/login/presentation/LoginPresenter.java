package fr.viper.login.presentation;

import android.content.Context;
import android.support.annotation.StringRes;
import android.text.format.DateUtils;

import fr.viper.core.entities.User;
import fr.viper.core.login.LoginOutputPort;

import static android.text.format.DateUtils.formatDateTime;

public class LoginPresenter implements LoginOutputPort {
    private final LoginView view;
    private final Context context;

    public LoginPresenter(LoginView view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void onEmptyId() {
        final LoginViewModel viewModel = new LoginViewModel();
        viewModel.error = getString(R.string.empty_id);
        view.displayViewModel(viewModel);
    }

    @Override
    public void onEmptyPassword() {
        final LoginViewModel viewModel = new LoginViewModel();
        viewModel.error = getString(R.string.empty_password);
        view.displayViewModel(viewModel);
    }

    @Override
    public void onPendingRequest() {
        final LoginViewModel viewModel = new LoginViewModel();
        viewModel.shouldDisplayLoading = true;
        view.displayViewModel(viewModel);
    }

    @Override
    public void onUnknownId() {
        final LoginViewModel viewModel = new LoginViewModel();
        viewModel.error = getString(R.string.unknown_id);
        viewModel.shouldDisplayForm = true;
        view.displayViewModel(viewModel);
    }

    @Override
    public void onInvalidPassword() {
        final LoginViewModel viewModel = new LoginViewModel();
        viewModel.error = getString(R.string.invalid_password);
        viewModel.shouldDisplayForm = true;
        view.displayViewModel(viewModel);
    }

    @Override
    public void onLoggedUser(User user) {
        final LoginViewModel viewModel = new LoginViewModel();
        viewModel.shouldDisplayLoggedUser = true;
        viewModel.title = getViewModelTitle(user);
        viewModel.description = getViewModelDescription(user);
        view.displayViewModel(viewModel);
    }

    private String getString(@StringRes int resId) {
        return context.getResources().getString(resId);
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

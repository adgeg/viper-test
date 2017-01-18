package fr.viper.login.presentation;

import android.content.Context;
import android.support.annotation.StringRes;
import android.text.format.DateUtils;

import fr.viper.core.entities.User;
import fr.viper.core.login.LoginPresenter;

import static android.text.format.DateUtils.formatDateTime;

public class AndroidLoginPresenter implements LoginPresenter {
    private final LoginView view;
    private final Context context;

    public AndroidLoginPresenter(LoginView view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void presentEmptyId() {
        final LoginViewModel viewModel = new LoginViewModel();
        viewModel.error = getString(R.string.empty_id);
        view.displayViewModel(viewModel);
    }

    @Override
    public void presentEmptyPassword() {
        final LoginViewModel viewModel = new LoginViewModel();
        viewModel.error = getString(R.string.empty_password);
        view.displayViewModel(viewModel);
    }

    @Override
    public void presentPendingRequest() {
        final LoginViewModel viewModel = new LoginViewModel();
        viewModel.loading = true;
        view.displayViewModel(viewModel);
    }

    @Override
    public void presentUnknownId() {
        final LoginViewModel viewModel = new LoginViewModel();
        viewModel.error = getString(R.string.unknown_id);
        viewModel.form = true;
        view.displayViewModel(viewModel);
    }

    @Override
    public void presentInvalidPassword() {
        final LoginViewModel viewModel = new LoginViewModel();
        viewModel.error = getString(R.string.invalid_password);
        viewModel.form = true;
        view.displayViewModel(viewModel);
    }

    @Override
    public void presentLoggedUser(User user) {
        final LoginViewModel viewModel = new LoginViewModel();
        viewModel.logged = true;
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

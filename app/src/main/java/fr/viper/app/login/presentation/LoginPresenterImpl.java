package fr.viper.app.login.presentation;

import android.content.Context;
import android.text.format.DateUtils;

import fr.viper.app.R;
import fr.viper.core.entities.User;
import fr.viper.core.login.LoginPresenter;

import static android.text.format.DateUtils.formatDateTime;
import static fr.viper.app.login.presentation.LoginViewModel.DISPLAY_FORM;
import static fr.viper.app.login.presentation.LoginViewModel.DISPLAY_LOADING;
import static fr.viper.app.login.presentation.LoginViewModel.DISPLAY_SUCCESS;

public class LoginPresenterImpl implements LoginPresenter {
    private final LoginView view;
    private final Context context;

    public LoginPresenterImpl(LoginView view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void presentEmptyId() {
        final LoginViewModel viewModel = new LoginViewModel();
        viewModel.errorResId = R.string.empty_id;
        view.displayViewModel(viewModel);
    }

    @Override
    public void presentEmptyPassword() {
        final LoginViewModel viewModel = new LoginViewModel();
        viewModel.errorResId = R.string.empty_password;
        view.displayViewModel(viewModel);
    }

    @Override
    public void presentPendingRequest() {
        final LoginViewModel viewModel = new LoginViewModel();
        viewModel.displayedChild = DISPLAY_LOADING;
        viewModel.shouldHideKeyboard = true;
        view.displayViewModel(viewModel);
    }

    @Override
    public void presentUnknownId() {
        final LoginViewModel viewModel = new LoginViewModel();
        viewModel.errorResId = R.string.unknown_id;
        viewModel.displayedChild = DISPLAY_FORM;
        view.displayViewModel(viewModel);
    }

    @Override
    public void presentInvalidPassword() {
        final LoginViewModel viewModel = new LoginViewModel();
        viewModel.errorResId = R.string.invalid_password;
        viewModel.displayedChild = DISPLAY_FORM;
        view.displayViewModel(viewModel);
    }

    @Override
    public void presentLoggedUser(User user) {
        final LoginViewModel viewModel = new LoginViewModel();
        viewModel.displayedChild = DISPLAY_SUCCESS;
        viewModel.title = getViewModelTitle(user);
        viewModel.description = getViewModelDescription(user);
        view.displayViewModel(viewModel);
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

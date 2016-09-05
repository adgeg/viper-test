package fr.viper.app.login.presentation;

import android.support.annotation.StringRes;

import java.util.concurrent.Executor;

import fr.viper.app.Attachable;

public class LoginViewDecorator implements LoginView, Attachable<LoginView> {
    private final Executor executor;
    private LoginView loginView;

    public LoginViewDecorator(Executor executor) {
        this.executor = executor;
    }

    @Override
    public void attach(LoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void detach() {
        this.loginView = null;
    }

    @Override
    public void displayLoading() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                if(loginView != null){
                    loginView.displayLoading();
                }
            }
        });
    }

    @Override
    public void displaySuccessfulLogin(final UserViewModel viewModel) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                if(loginView != null){
                    loginView.displaySuccessfulLogin(viewModel);
                }
            }
        });
    }

    @Override
    public void displayErrorMessage(@StringRes final int messageResId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                if (loginView != null) {
                    loginView.displayErrorMessage(messageResId);
                }
            }
        });
    }
}

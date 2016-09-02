package fr.viper.app.login;

import java.util.concurrent.Executor;

import fr.viper.app.Attachable;
import fr.viper.core.entities.User;

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
    public void displayEmptyPassword() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                if(loginView != null){
                    loginView.displayEmptyPassword();
                }
            }
        });
    }

    @Override
    public void displayEmptyUserName() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                if(loginView != null){
                    loginView.displayEmptyUserName();
                }
            }
        });
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
    public void displayUnknownName() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                if(loginView != null){
                    loginView.displayUnknownName();
                }
            }
        });
    }

    @Override
    public void displayInvalidPassword() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                if(loginView != null){
                    loginView.displayInvalidPassword();
                }
            }
        });
    }

    @Override
    public void displaySuccessfulLogin(final User user) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                if(loginView != null){
                    loginView.displaySuccessfulLogin(user);
                }
            }
        });
    }
}

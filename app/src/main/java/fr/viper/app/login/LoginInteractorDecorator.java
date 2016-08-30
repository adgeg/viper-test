package fr.viper.app.login;

import java.util.concurrent.Executor;

import fr.viper.core.login.LoginInteractor;
import fr.viper.core.login.LoginRequest;

public class LoginInteractorDecorator implements LoginInteractor{
    private final LoginInteractor interactor;
    private final Executor executor;

    public LoginInteractorDecorator(LoginInteractor interactor, Executor executor) {
        this.interactor = interactor;
        this.executor = executor;
    }

    @Override
    public void login(final String name, final String password) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                interactor.login(name,password);
            }
        });
    }

    @Override
    public void login(final LoginRequest request) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                interactor.login(request);
            }
        });
    }
}

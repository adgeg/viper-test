package fr.viper.app.login.controller;

import fr.viper.core.login.LoginInteractor;
import fr.viper.core.login.LoginRequest;

public class LoginControllerImpl implements LoginController {
    private final LoginInteractor interactor;

    public LoginControllerImpl(LoginInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void executeRequest(String name, String password) {
        interactor.login(new LoginRequest(name, password));
    }
}

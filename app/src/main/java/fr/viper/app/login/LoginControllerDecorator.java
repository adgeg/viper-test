package fr.viper.app.login;

import java.util.concurrent.Executor;

public class LoginControllerDecorator implements LoginController {
    private final LoginController controller;
    private final Executor executor;

    public LoginControllerDecorator(LoginController controller, Executor executor) {
        this.controller = controller;
        this.executor = executor;
    }

    @Override
    public void executeRequest(final String name, final String password) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                controller.executeRequest(name, password);
            }
        });
    }
}

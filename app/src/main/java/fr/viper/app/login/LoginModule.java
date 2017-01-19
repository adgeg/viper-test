package fr.viper.app.login;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.viper.app.ApplicationModule;
import fr.viper.app.login.controller.LoginController;
import fr.viper.app.login.controller.LoginControllerDecorator;
import fr.viper.app.login.controller.LoginControllerImpl;
import fr.viper.core.login.LoginInteractor;
import fr.viper.core.login.LoginOutputPort;
import fr.viper.core.login.LoginRepository;
import fr.viper.login.presentation.LoginPresenter;
import fr.viper.login.presentation.LoginView;
import fr.viper.repositories.login.FakeLoginRepository;
import fr.viper.repositories.login.SimulateDelayLoginRepository;

public class LoginModule {
    private final ApplicationModule applicationModule;
    private final LoginView view;

    public LoginModule(ApplicationModule applicationModule, LoginView view) {
        this.applicationModule = applicationModule;
        this.view = view;
    }

    public LoginController getController() {
        final LoginInteractor interactor = new LoginInteractor(getRepository(), getOutputPort());
        final LoginControllerImpl controller = new LoginControllerImpl(interactor);
        return new LoginControllerDecorator(controller, applicationModule.getAsyncExecutor());
    }

    private LoginRepository getRepository() {
        final ObjectMapper objectMapper = applicationModule.getMapperModule().getObjectMapper();
        final FakeLoginRepository repository = new FakeLoginRepository(objectMapper);
        return new SimulateDelayLoginRepository(repository);
    }

    private LoginOutputPort getOutputPort() {
        return new LoginPresenter(view, applicationModule.getContext());
    }
}

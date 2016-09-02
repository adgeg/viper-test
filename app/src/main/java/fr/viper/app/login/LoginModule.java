package fr.viper.app.login;

import fr.viper.app.ApplicationModule;
import fr.viper.app.Attachable;
import fr.viper.core.login.LoginInteractor;
import fr.viper.core.login.LoginPresenter;
import fr.viper.core.login.LoginRepository;
import fr.viper.gateway.MockLoginRepository;

public class LoginModule {
    private final ApplicationModule applicationModule;
    private final LoginViewDecorator viewDecorator;

    public LoginModule(ApplicationModule applicationModule) {
        this.applicationModule = applicationModule;
        this.viewDecorator = new LoginViewDecorator(applicationModule.getUiThreadExecutor());
    }

    public LoginController getController() {
        final LoginInteractor interactor = new LoginInteractor(getGateway(), getPresenter());
        final LoginControllerImpl controller = new LoginControllerImpl(interactor);
        return new LoginControllerDecorator(controller, applicationModule.getAsyncExecutor());
    }

    public Attachable<LoginView> getAttachableView(){
        return viewDecorator;
    }

    private LoginRepository getGateway() {
        return new MockLoginRepository();
    }

    private LoginPresenter getPresenter(){
        return new LoginPresenterImpl(viewDecorator, applicationModule.getContext());
    }
}

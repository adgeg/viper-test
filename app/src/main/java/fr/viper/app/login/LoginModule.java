package fr.viper.app.login;

import fr.viper.app.ApplicationModule;
import fr.viper.app.Attachable;
import fr.viper.core.login.LoginGateway;
import fr.viper.core.login.LoginInteractor;
import fr.viper.core.login.LoginInteractorImpl;
import fr.viper.core.login.LoginPresenter;
import fr.viper.gateway.MockLoginGateway;

public class LoginModule {
    private final ApplicationModule applicationModule;
    private final LoginViewDecorator viewDecorator;

    public LoginModule(ApplicationModule applicationModule) {
        this.applicationModule = applicationModule;
        this.viewDecorator = new LoginViewDecorator(applicationModule.getUiThreadExecutor());
    }

    public LoginInteractor getInteractor(){
        final LoginInteractor interactor = new LoginInteractorImpl(getGateway(), getPresenter());
        return new LoginInteractorDecorator(interactor,applicationModule.getAsyncExecutor());
    }

    public Attachable<LoginView> getAttachableView(){
        return viewDecorator;
    }

    private LoginGateway getGateway(){
        return new MockLoginGateway();
    }

    private LoginPresenter getPresenter(){
        return new LoginPresenterImpl(viewDecorator);
    }
}

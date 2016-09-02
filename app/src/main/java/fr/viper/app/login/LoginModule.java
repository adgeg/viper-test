package fr.viper.app.login;

import fr.viper.app.ApplicationModule;
import fr.viper.app.Attachable;
import fr.viper.core.login.LoginInteractor;
import fr.viper.core.login.LoginPresenter;
import fr.viper.core.login.LoginRepository;
import fr.viper.repositories.login.FakeLoginRepository;

public class LoginModule {
    private final ApplicationModule applicationModule;
    private final LoginViewDecorator viewDecorator;

    public LoginModule(ApplicationModule applicationModule) {
        this.applicationModule = applicationModule;
        this.viewDecorator = new LoginViewDecorator(applicationModule.getUiThreadExecutor());
    }

    public LoginController getController() {
        final LoginInteractor interactor = new LoginInteractor(getRepository(), getPresenter());
        final LoginControllerImpl controller = new LoginControllerImpl(interactor);
        return new LoginControllerDecorator(controller, applicationModule.getAsyncExecutor());
    }

    public Attachable<LoginView> getAttachableView(){
        return viewDecorator;
    }

    private LoginRepository getRepository() {
        return new FakeLoginRepository(applicationModule.getMapperModule().getObjectMapper());
    }

    private LoginPresenter getPresenter(){
        return new LoginPresenterImpl(viewDecorator, applicationModule.getContext());
    }
}

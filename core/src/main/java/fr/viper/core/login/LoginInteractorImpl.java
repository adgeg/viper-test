package fr.viper.core.login;

import static fr.viper.core.login.LoginGateway.InvalidPasswordException;
import static fr.viper.core.login.LoginGateway.UnknownUserException;
import static fr.viper.core.utils.StringUtils.isEmpty;

public class LoginInteractorImpl implements LoginInteractor {
    private final LoginGateway gateway;
    private final LoginPresenter presenter;

    public LoginInteractorImpl(LoginGateway gateway, LoginPresenter presenter) {
        this.gateway = gateway;
        this.presenter = presenter;
    }

    @Override
    public void login(String name, String password) {
        login(new LoginRequest(name, password));
    }

    @Override
    public void login(LoginRequest request) {
        if (credentialsAreInvalid(request)) {
            handleInvalidCredentials(request);
        } else {
            handleValidCredentials(request);
        }
    }

    private void handleValidCredentials(LoginRequest request) {
        presenter.displayLoading();
        String loginMessage = null;
        try {
            loginMessage = gateway.checkCredentials(request);
        } catch (UnknownUserException e) {
            handleUnknownUser(e);
        } catch (InvalidPasswordException e) {
            handleInvalidPassword(e);
        }

        if (loginIsSuccessful(loginMessage)) {
            presenter.displaySuccessfulLogin(new LoginResponse(loginMessage));
        }
    }

    private void handleInvalidPassword(InvalidPasswordException e) {
        e.printStackTrace();
        presenter.displayInvalidPassword();
    }

    private void handleUnknownUser(UnknownUserException e) {
        e.printStackTrace();
        presenter.displayUnknownName();
    }

    private boolean loginIsSuccessful(String loginMessage) {
        return loginMessage != null;
    }

    private void handleInvalidCredentials(LoginRequest request) {
        if (isEmpty(request.getName())) {
            presenter.displayEmptyUserName();
        } else if (isEmpty(request.getPassword())) {
            presenter.displayEmptyPassword();
        }
    }

    private boolean credentialsAreInvalid(LoginRequest request) {
        return isEmpty(request.getName()) || isEmpty(request.getPassword());
    }
}

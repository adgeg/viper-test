package fr.viper.core.login;

import fr.viper.core.entities.User;

import static fr.viper.core.login.LoginRepository.InvalidPasswordException;
import static fr.viper.core.login.LoginRepository.UnknownUserException;
import static fr.viper.core.utils.StringUtils.isEmpty;

public class LoginInteractor {
    private final LoginRepository gateway;
    private final LoginPresenter presenter;

    public LoginInteractor(LoginRepository gateway, LoginPresenter presenter) {
        this.gateway = gateway;
        this.presenter = presenter;
    }

    public void login(LoginRequest request) {
        if (credentialsAreInvalid(request)) {
            handleInvalidCredentials(request);
        } else {
            handleValidCredentials(request);
        }
    }

    private void handleValidCredentials(LoginRequest request) {
        presenter.displayLoading();
        User user = null;
        try {
            user = gateway.getUser(request);
        } catch (UnknownUserException e) {
            handleUnknownUser(e);
        } catch (InvalidPasswordException e) {
            handleInvalidPassword(e);
        }

        if (loginIsSuccessful(user)) {
            presenter.displayLoggedUser(user);
        }
    }

    private void handleInvalidPassword(InvalidPasswordException e) {
        e.printStackTrace();
        presenter.displayInvalidPassword();
    }

    private void handleUnknownUser(UnknownUserException e) {
        e.printStackTrace();
        presenter.displayUnknownId();
    }

    private boolean loginIsSuccessful(User user) {
        return user != null;
    }

    private void handleInvalidCredentials(LoginRequest request) {
        if (isEmpty(request.getId())) {
            presenter.displayEmptyId();
        } else if (isEmpty(request.getPassword())) {
            presenter.displayEmptyPassword();
        }
    }

    private boolean credentialsAreInvalid(LoginRequest request) {
        return isEmpty(request.getId()) || isEmpty(request.getPassword());
    }
}

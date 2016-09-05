package fr.viper.core.login;

import fr.viper.core.entities.User;

import static fr.viper.core.login.LoginRepository.InvalidPasswordException;
import static fr.viper.core.login.LoginRepository.UnknownUserException;
import static fr.viper.core.utils.StringUtils.isEmpty;

public class LoginInteractor {
    private final LoginRepository repository;
    private final LoginPresenter presenter;

    public LoginInteractor(LoginRepository repository, LoginPresenter presenter) {
        this.repository = repository;
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
        presenter.presentPendingRequest();
        User user = null;
        try {
            user = repository.getUser(request);
        } catch (UnknownUserException e) {
            handleUnknownUser(e);
        } catch (InvalidPasswordException e) {
            handleInvalidPassword(e);
        }

        if (loginIsSuccessful(user)) {
            presenter.presentLoggedUser(user);
        }
    }

    private void handleInvalidPassword(InvalidPasswordException e) {
        e.printStackTrace();
        presenter.presentInvalidPassword();
    }

    private void handleUnknownUser(UnknownUserException e) {
        e.printStackTrace();
        presenter.presentUnknownId();
    }

    private boolean loginIsSuccessful(User user) {
        return user != null;
    }

    private void handleInvalidCredentials(LoginRequest request) {
        if (isEmpty(request.getId())) {
            presenter.presentEmptyId();
        } else if (isEmpty(request.getPassword())) {
            presenter.presentEmptyPassword();
        }
    }

    private boolean credentialsAreInvalid(LoginRequest request) {
        return isEmpty(request.getId()) || isEmpty(request.getPassword());
    }
}

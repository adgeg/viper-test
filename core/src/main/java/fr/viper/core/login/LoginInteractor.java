package fr.viper.core.login;

import fr.viper.core.entities.User;

import static fr.viper.core.login.LoginRepository.InvalidPasswordException;
import static fr.viper.core.login.LoginRepository.UnknownUserException;
import static fr.viper.core.utils.StringUtils.isEmpty;
import static fr.viper.core.utils.StringUtils.isNotEmpty;

public class LoginInteractor {
    private final LoginRepository repository;
    private final LoginOutputPort outputPort;

    public LoginInteractor(LoginRepository repository, LoginOutputPort outputPort) {
        this.repository = repository;
        this.outputPort = outputPort;
    }

    public void login(LoginRequest request) {
        if (credentialsAreValid(request)) {
            handleValidCredentials(request);
        } else {
            handleInvalidCredentials(request);
        }
    }

    private void handleValidCredentials(LoginRequest request) {
        outputPort.onPendingRequest();
        User user = null;
        try {
            user = repository.getUser(request);
        } catch (UnknownUserException e) {
            handleUnknownUser(e);
        } catch (InvalidPasswordException e) {
            handleInvalidPassword(e);
        }

        if (loginIsSuccessful(user)) {
            outputPort.onLoggedUser(user);
        }
    }

    private void handleInvalidPassword(InvalidPasswordException e) {
        e.printStackTrace();
        outputPort.onInvalidPassword();
    }

    private void handleUnknownUser(UnknownUserException e) {
        e.printStackTrace();
        outputPort.onUnknownId();
    }

    private boolean loginIsSuccessful(User user) {
        return user != null;
    }

    private void handleInvalidCredentials(LoginRequest request) {
        if (isEmpty(request.getId())) {
            outputPort.onEmptyId();
        }
        if (isEmpty(request.getPassword())) {
            outputPort.onEmptyPassword();
        }
    }

    private boolean credentialsAreValid(LoginRequest request) {
        return isNotEmpty(request.getId()) && isNotEmpty(request.getPassword());
    }
}

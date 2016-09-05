package fr.viper.app.login;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import fr.viper.app.R;
import fr.viper.app.databinding.ActivityLoginBinding;
import fr.viper.app.login.presentation.LoginView;
import fr.viper.app.login.presentation.UserViewModel;

import static fr.viper.app.ViperApplication.getApplicationModule;

public class LoginActivity extends AppCompatActivity implements LoginView {
    private static final int DISPLAY_FORM = 0;
    private static final int DISPLAY_LOADING = 1;
    private static final int DISPLAY_SUCCESS = 2;
    private LoginModule loginModule;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginModule = new LoginModule(getApplicationModule(this));
        loginModule.getAttachableView().attach(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setController(loginModule.getController());
    }

    @Override
    protected void onDestroy() {
        loginModule.getAttachableView().detach();
        super.onDestroy();
    }

    @Override
    public void displayLoading() {
        hideKeyboard();
        binding.viewFlipper.setDisplayedChild(DISPLAY_LOADING);
    }

    @Override
    public void displaySuccessfulLogin(UserViewModel viewModel) {
        binding.setViewModel(viewModel);
        binding.viewFlipper.setDisplayedChild(DISPLAY_SUCCESS);
    }

    @Override
    public void displayErrorMessage(@StringRes int messageResId) {
        displayForm();
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    private void displayForm() {
        if (binding.viewFlipper.getDisplayedChild() != DISPLAY_FORM) {
            binding.viewFlipper.setDisplayedChild(DISPLAY_FORM);
        }
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.passwordView.getWindowToken(), 0);
    }
}

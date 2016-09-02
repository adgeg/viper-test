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
    public void displayEmptyPassword() {
        toast(R.string.empty_password);
    }

    @Override
    public void displayEmptyId() {
        toast(R.string.empty_id);
    }

    @Override
    public void displayLoading() {
        hideKeyboard();
        binding.viewFlipper.setDisplayedChild(DISPLAY_LOADING);
    }

    @Override
    public void displayUnknownId() {
        binding.viewFlipper.setDisplayedChild(DISPLAY_FORM);
        toast(R.string.unknown_id);
    }

    @Override
    public void displayInvalidPassword() {
        binding.viewFlipper.setDisplayedChild(DISPLAY_FORM);
        toast(R.string.invalid_id);
    }

    @Override
    public void displaySuccessfulLogin(UserViewModel viewModel) {
        binding.setViewModel(viewModel);
        binding.viewFlipper.setDisplayedChild(DISPLAY_SUCCESS);
    }

    private void toast(@StringRes int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.passwordView.getWindowToken(), 0);
    }
}

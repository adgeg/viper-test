package fr.viper.app.login;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import fr.viper.app.R;
import fr.viper.app.databinding.ActivityLoginBinding;

import static fr.viper.app.ViperApplication.getApplicationModule;

public class LoginActivity extends AppCompatActivity implements LoginView {
    private static final int DISPLAY_FORM = 0;
    private static final int DISPLAY_LOADING = 1;
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
        displayForm();
        toast("Empty password");
    }

    @Override
    public void displayEmptyUserName() {
        displayForm();
        toast("Empty user name");
    }

    @Override
    public void displayLoading() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.passwordView.getWindowToken(), 0);
        binding.viewFlipper.setDisplayedChild(DISPLAY_LOADING);
    }

    @Override
    public void displayUnknownName() {
        displayForm();
        toast("Unknown user name");
    }

    @Override
    public void displayInvalidPassword() {
        displayForm();
        toast("Invalid password");
    }

    @Override
    public void displaySuccessfulLogin(String message) {
        displayForm();
        toast(message);
    }

    private void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private void displayForm() {
        if (binding.viewFlipper.getDisplayedChild() != DISPLAY_FORM) {
            binding.viewFlipper.setDisplayedChild(DISPLAY_FORM);
        }
    }
}

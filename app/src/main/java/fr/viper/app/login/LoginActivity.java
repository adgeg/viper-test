package fr.viper.app.login;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import fr.viper.app.R;
import fr.viper.app.databinding.ActivityLoginBinding;
import fr.viper.app.login.presentation.LoginView;
import fr.viper.app.login.presentation.LoginViewModel;

import static fr.viper.app.ViperApplication.getApplicationModule;

public class LoginActivity extends AppCompatActivity implements LoginView {
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoginModule loginModule = new LoginModule(getApplicationModule(this), this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setController(loginModule.getController());
    }

    @Override
    protected void onDestroy() {
        binding.unbind();
        super.onDestroy();
    }

    @Override
    public void displayViewModel(LoginViewModel viewModel) {
        binding.setViewModel(viewModel);
    }
}

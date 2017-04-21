package com.project.smarthome.smarthome.Views.LogInRegister.Shared;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.project.smarthome.smarthome.Model.ConfigService;
import com.project.smarthome.smarthome.Presenters.LogInRegister.Shared.LogInRegisterPresenter;
import com.project.smarthome.smarthome.R;
import com.project.smarthome.smarthome.Views.Home.HomeActivity;
import com.project.smarthome.smarthome.Views.LogInRegister.Register.QRScannerFragment;
import com.project.smarthome.smarthome.Views.LogInRegister.Register.RegisterDetailsFragment;

public class LogInRegisterActivity extends AppCompatActivity
        implements QRScannerFragment.QRScannerInteractionListener, LogInRegisterView {

    private static final String TAG = LogInRegisterActivity.class.getSimpleName();

    private LogInRegisterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_register);

        presenter = new LogInRegisterPresenter(this, new ConfigService(this));
        presenter.onStart();
    }

    @Override
    public void onCodeEntered(final String code) {
        presenter.onHomeQrCodeEntered(code);
    }

    @Override
    public void openHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLogInOrRegisterChoice() {
        LogInRegisterHomeFragment fragment = LogInRegisterHomeFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_placeholder, fragment)
                .commitAllowingStateLoss();
    }

    @Override
    public void showRegistrationDetailsForm(String code) {
        RegisterDetailsFragment fragment = RegisterDetailsFragment.newInstance(code);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_placeholder, fragment)
                .commitAllowingStateLoss();
    }
}
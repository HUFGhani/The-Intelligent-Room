package com.project.smarthome.smarthome.Views.LogInRegister.LogIn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.project.smarthome.smarthome.Model.ConfigService;
import com.project.smarthome.smarthome.Model.LogInRegister.LogInRequest;
import com.project.smarthome.smarthome.Presenters.LogInRegister.LogIn.LogInDetailsPresenter;
import com.project.smarthome.smarthome.R;
import com.project.smarthome.smarthome.Views.Home.HomeActivity;
import com.project.smarthome.smarthome.databinding.FragmentLoginDetailsBinding;

public class LogInDetailsFragment extends Fragment implements LogInDetailsView {

    private FragmentLoginDetailsBinding binding;
    private LogInDetailsPresenter presenter;
    private LogInRequest logInRequest;

    public LogInDetailsFragment() {
        // Required empty public constructor
    }

    public static LogInDetailsFragment newInstance() {
        return new LogInDetailsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new LogInDetailsPresenter(this, new ConfigService(getContext()));
        presenter.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginDetailsBinding.inflate(inflater, container, false);
        logInRequest = new LogInRequest();
        binding.setLogInRequest(logInRequest);

        binding.btnLogInSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onLogInPressed(logInRequest);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void setTitle() {
        getActivity().setTitle(getString(R.string.log_in));
    }

    @Override
    public void showEmailError() {
        binding.tilLogInEmail.setError(getString(R.string.error_invalid_email));
    }

    @Override
    public void hideEmailError() {
        binding.tilLogInEmail.setError(null);
    }

    @Override
    public void showPasswordError() {
        binding.tilLogInUserPassword.setError(getString(R.string.error_invalid_password));
    }

    @Override
    public void hidePasswordError() {
        binding.tilLogInUserPassword.setError(null);
    }

    @Override
    public void showLogInError() {
        showToast(getString(R.string.error_logging_in));
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openHomeActivity() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
    }
}

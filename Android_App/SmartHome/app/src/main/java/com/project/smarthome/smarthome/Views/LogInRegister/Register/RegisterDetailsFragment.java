package com.project.smarthome.smarthome.Views.LogInRegister.Register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.project.smarthome.smarthome.Model.ConfigService;
import com.project.smarthome.smarthome.Model.LogInRegister.RegistrationRequest;
import com.project.smarthome.smarthome.Presenters.LogInRegister.Register.RegisterDetailsPresenter;
import com.project.smarthome.smarthome.R;
import com.project.smarthome.smarthome.Views.Home.HomeActivity;
import com.project.smarthome.smarthome.databinding.FragmentRegisterDetailsBinding;

public class RegisterDetailsFragment extends Fragment implements RegisterDetailsView {

    private static final String TAG = RegisterDetailsFragment.class.getSimpleName();

    private FragmentRegisterDetailsBinding binding;
    private RegistrationRequest registrationRequest;
    private RegisterDetailsPresenter presenter;

    public RegisterDetailsFragment() {
        // Required empty public constructor
    }

    public static RegisterDetailsFragment newInstance(String homeId) {
        RegisterDetailsFragment fragment = new RegisterDetailsFragment();
        Bundle args = new Bundle();
        args.putString("homeId", homeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        assert (bundle != null);
        assert (bundle.getString("homeId", null) != null);

        registrationRequest = new RegistrationRequest();
        registrationRequest.setHomeId(bundle.getString("homeId"));

        presenter = new RegisterDetailsPresenter(this, new ConfigService(getContext()));
        presenter.onLoad();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegisterDetailsBinding.inflate(inflater, container, false);
        binding.setRegistrationRequest(registrationRequest);

        binding.btnRegisterUserDetailsSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onUserPressedSubmit(registrationRequest);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void setTitle() {
        getActivity().setTitle(getString(R.string.register));
    }

    @Override
    public void showHomePasswordError() {
        binding.tilRegisterHomePassword.setError(getString(R.string.error_invalid_password));
    }

    @Override
    public void hideHomePasswordError() {
        binding.tilRegisterHomePassword.setError(null);
    }

    @Override
    public void showFirstNameError() {
        binding.tilRegisterFirstName.setError(getString(R.string.error_invalid_name));
    }

    @Override
    public void hideFirstNameError() {
        binding.tilRegisterFirstName.setError(null);
    }

    @Override
    public void showLastNameError() {
        binding.tilRegisterLastName.setError(getString(R.string.error_invalid_name));
    }

    @Override
    public void hideLastNameError() {
        binding.tilRegisterLastName.setError(null);
    }

    @Override
    public void showEmailError() {
        binding.tilRegisterEmail.setError(getString(R.string.error_invalid_email));
    }

    @Override
    public void hideEmailError() {
        binding.tilRegisterEmail.setError(null);
    }

    @Override
    public void showUserPasswordError() {
        binding.tilRegisterUserPassword.setError(getString(R.string.error_invalid_password));
    }

    @Override
    public void hideUserPasswordError() {
        binding.tilRegisterUserPassword.setError(null);
    }

    @Override
    public void showToast(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openHomeActivity() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
    }
}
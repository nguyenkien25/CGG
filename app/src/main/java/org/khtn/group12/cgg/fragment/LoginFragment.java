package org.khtn.group12.cgg.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.khtn.group12.cgg.R;
import org.khtn.group12.cgg.activity.LoginRegisterActivity;
import org.khtn.group12.cgg.utils.AppController;
import org.khtn.group12.cgg.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginFragment extends Fragment {

    private static final String TAG = LoginFragment.class.getSimpleName();

    @BindView(R.id.edtNumberPhone)
    EditText edtNumberPhone;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.btnRegister)
    Button btnRegister;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupContentUI();
    }

    private void setupContentUI() {
        edtPassword.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
                attemptLogin();
                return true;
            } else {
                return false;
            }
        });
        btnLogin.setOnClickListener(v -> attemptLogin());
        btnRegister.setOnClickListener(v -> {
            ((LoginRegisterActivity) getActivity()).loadFragment(Constants.REGISTER);
        });
    }

    private boolean isPasswordValid(String password) {
        return (password.length() > 4);
    }

    private void attemptLogin() {
        // Reset errors.
        edtNumberPhone.setError(null);
        edtPassword.setError(null);

        // Store values at the time of the login attempt.
        String numberPhone = edtNumberPhone.getText().toString();
        String password = edtPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            edtPassword.setError(getString(R.string.error_invalid_password));
            focusView = edtPassword;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(numberPhone)) {
            edtNumberPhone.setError(getString(R.string.error_field_required));
            focusView = edtNumberPhone;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            performFireBaseLogin(Constants.PEX_PHONE + numberPhone.substring(1) + Constants.EXTENSION_EMAIL, password);
        }
    }

    private void performFireBaseLogin(String email, String password) {
        FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getActivity(), getString(R.string.login_successful), Toast.LENGTH_LONG).show();
                        AppController.getInstance().getPrefManager().storeUID(task.getResult().getUser().getUid());
                        ((LoginRegisterActivity) getActivity()).goToMain();
                    } else {
                        Toast.makeText(getActivity(), getString(R.string.login_fail), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
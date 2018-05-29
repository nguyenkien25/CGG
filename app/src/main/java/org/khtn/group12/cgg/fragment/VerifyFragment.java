package org.khtn.group12.cgg.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.khtn.group12.cgg.R;
import org.khtn.group12.cgg.activity.LoginRegisterActivity;
import org.khtn.group12.cgg.utils.Constants;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VerifyFragment extends Fragment {

    private static final String TAG = VerifyFragment.class.getSimpleName();

    @BindView(R.id.pinEntry)
    PinEntryEditText pinEntry;
    @BindView(R.id.btnResend)
    Button btnResend;
    @BindView(R.id.btnNext)
    Button btnNext;

    private String numberPhone = "";
    private String password = "";
    private FirebaseAuth auth;
    private String verificationId;
    private PhoneAuthProvider.ForceResendingToken resendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;

    // TODO: Timer
    private Handler customHandler;
    private Long timeInMilliseconds = 0L;
    private Long timeSwapBuff = 0L;
    private Long updatedTime = 0L;
    private Long startTime = 0L;

    public VerifyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_verify, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupContentUI();
    }

    private void setupContentUI() {
        numberPhone = ((LoginRegisterActivity) getActivity()).getNumberPhoneRegister();
        password = ((LoginRegisterActivity) getActivity()).getPasswordRegister();

        FirebaseApp.initializeApp(getActivity());
        auth = FirebaseAuth.getInstance();
        auth.setLanguageCode("vi");
        initCallbackVerify();
        sendCodeVerify(numberPhone, callbacks);

        btnResend.setOnClickListener(v -> {
            btnResend.setEnabled(false);
            resendVerificationCode(numberPhone, callbacks, resendToken);
        });

        btnNext.setOnClickListener(v -> {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, pinEntry.getText().toString());
            signInWithPhoneAuthCredential(credential);
        });
        pinEntry.setOnPinEnteredListener(str -> btnNext.setEnabled((str.toString().length() == 6)));
    }

    private void sendCodeVerify(String phoneNumber, PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,                                    // Phone number to verify
                Constants.TIME_OUT_SEND_CODE,                   // Timeout duration
                TimeUnit.SECONDS,                               // Unit of timeout
                getActivity(),                                  // Activity (for callback binding)
                callbacks);                                      // OnVerificationStateChangedCallbacks
    }

    private void resendVerificationCode(String phoneNumber, PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks, PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,                                    // Phone number to verify
                Constants.TIME_OUT_SEND_CODE,                   // Timeout duration
                TimeUnit.SECONDS,                               // Unit of timeout
                getActivity(),                                  // Activity (for callback binding)
                callbacks,                                      // OnVerificationStateChangedCallbacks
                token);                                         // ForceResendingToken from callbacks
    }

    private void initCallbackVerify() {
        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // verification completed
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked if an invalid request for verification is made,
                // for instance if the the phone number format is invalid.
                Toast.makeText(getActivity(), getString(R.string.send_code_fail), Toast.LENGTH_LONG).show();

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    Toast.makeText(getActivity(), getString(R.string.number_phone_fail), Toast.LENGTH_LONG).show();
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    Toast.makeText(getActivity(), getString(R.string.quota_exceeded), Toast.LENGTH_LONG).show();
                }
                e.printStackTrace();
            }

            @Override
            public void onCodeSent(String verifyId, PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Toast.makeText(getActivity(), getString(R.string.send_code) + " " + numberPhone, Toast.LENGTH_LONG).show();
                // Save verification ID and resending token so we can use them later
                verificationId = verifyId;
                resendToken = token;

                startTime = System.currentTimeMillis();
                customHandler = new Handler();
                customHandler.postDelayed(updateTimerThread, 1000);
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(String verificationId) {
                // called when the timeout duration has passed without triggering onVerificationCompleted
                super.onCodeAutoRetrievalTimeOut(verificationId);
            }
        };
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        auth.signOut();
                        performFirebaseRegistration(numberPhone + Constants.EXTENSION_EMAIL, password);
                    } else {
                        // Sign in failed, display a message and update the UI
                        Toast.makeText(getActivity(), getString(R.string.register_fail), Toast.LENGTH_LONG).show();
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
                            Toast.makeText(getActivity(), getString(R.string.code_fail), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void performFirebaseRegistration(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getActivity(), getString(R.string.register_successful), Toast.LENGTH_LONG).show();
                        ((LoginRegisterActivity) getActivity()).goToMain();
                    } else {
                        Toast.makeText(getActivity(), getString(R.string.register_fail), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            timeInMilliseconds = System.currentTimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;
            Long seconds = Constants.TIME_OUT_SEND_CODE - (updatedTime / 1000);
            if (seconds == 0L) {
                btnResend.setText(getString(R.string.resend));
                btnResend.setEnabled(true);
            } else {
                if (btnResend != null) {
                    btnResend.setText(getString(R.string.resend) + "(" + seconds + ")");
                    customHandler.postDelayed(this, 1000);
                }
            }
        }
    };

}
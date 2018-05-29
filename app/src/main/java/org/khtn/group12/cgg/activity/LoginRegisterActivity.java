package org.khtn.group12.cgg.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.khtn.group12.cgg.R;
import org.khtn.group12.cgg.fragment.LoginFragment;
import org.khtn.group12.cgg.fragment.RegisterFragment;
import org.khtn.group12.cgg.fragment.VerifyFragment;
import org.khtn.group12.cgg.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginRegisterActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbarLoginRegister;
    private String numberPhoneRegister;
    private String passwordRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        ButterKnife.bind(this);
        setupContentUI();
    }

    private void setupContentUI() {
        setSupportActionBar(toolbarLoginRegister);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black);

        Intent intent = getIntent();
        if (intent != null) {
            String flag = intent.getStringExtra(Constants.FLAG_LOGIN);
            loadFragment(flag);
        }
    }

    public void loadFragment(String flag) {
        if (flag != null && !flag.isEmpty()) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, getFragment(flag))
                    .addToBackStack(null)
                    .commit();
        }
    }

    private Fragment getFragment(String flag) {
        if (flag.equals(Constants.LOGIN)) {
            return new LoginFragment();
        }
        if (flag.equals(Constants.REGISTER)) {
            return new RegisterFragment();
        }
        if (flag.equals(Constants.VERIFY)) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black);
            return new VerifyFragment();
        }
        return null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void goToMain() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    public void gotoVerify(String number, String password) {
        this.numberPhoneRegister = Constants.PEX_PHONE + number.substring(1);
        this.passwordRegister = password;
        loadFragment(Constants.VERIFY);
    }

    public String getNumberPhoneRegister() {
        return numberPhoneRegister;
    }

    public void setNumberPhoneRegister(String numberPhoneRegister) {
        this.numberPhoneRegister = numberPhoneRegister;
    }

    public String getPasswordRegister() {
        return passwordRegister;
    }

    public void setPasswordRegister(String passwordRegister) {
        this.passwordRegister = passwordRegister;
    }
}

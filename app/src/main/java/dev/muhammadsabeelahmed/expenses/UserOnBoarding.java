package dev.muhammadsabeelahmed.expenses;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import dev.muhammadsabeelahmed.expenses.splash.SplashActivity;

public class UserOnBoarding extends AppCompatActivity implements View.OnClickListener {
    EditText first_name, last_name, email, phone;
    MaterialButton btn_onboard;
    PreferencesHandler preferencesHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_on_boarding);
        init();
    }

    private void init() {
        preferencesHandler = new PreferencesHandler(UserOnBoarding.this);
        first_name = findViewById(R.id.txt_firstname);
        last_name = findViewById(R.id.txt_lastname);
        email = findViewById(R.id.txt_email);
        phone = findViewById(R.id.txt_phone);
        btn_onboard = findViewById(R.id.button_onboard);
        btn_onboard.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_onboard:
                if (validateUser()) {
                    preferencesHandler.setAppFirstTime("false");
                    preferencesHandler.setUname(txtfirst + " " + txtlast);
                    preferencesHandler.setUemail(txtemail);
                    preferencesHandler.setUcontact(txtphone);
                    User.username = txtfirst + " " + txtlast;
                    User.useremail=txtemail;
                    startActivity(new Intent(UserOnBoarding.this, SplashActivity.class));
                    finish();
                }
                break;
        }
    }

    String txtfirst, txtlast, txtemail, txtphone = "";

    private boolean validateUser() {
        boolean valid = true;
        txtfirst = first_name.getText().toString();
        txtlast = last_name.getText().toString();
        txtemail = email.getText().toString();
        txtphone = phone.getText().toString();
        if (txtfirst.isEmpty() || txtfirst.length() < 2) {
            Toast.makeText(UserOnBoarding.this, "First Name required", Toast.LENGTH_SHORT).show();
            valid = false;
        } else if (txtlast.isEmpty() || txtlast.length() < 2) {
            Toast.makeText(UserOnBoarding.this, "Last Name required", Toast.LENGTH_SHORT).show();
            valid = false;
        } else if (txtemail.isEmpty() || !txtemail.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            Toast.makeText(UserOnBoarding.this, "Email Address Required", Toast.LENGTH_SHORT).show();
            valid = false;
        } else if (txtphone.isEmpty()) {
            Toast.makeText(UserOnBoarding.this, "Contact No. required", Toast.LENGTH_SHORT).show();
            valid = false;
        } else if (txtphone.length() < 9) {
            Toast.makeText(UserOnBoarding.this, "Contact No. length should be greater then 9", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        return valid;
    }
}
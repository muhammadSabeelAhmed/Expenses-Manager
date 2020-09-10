package dev.muhammadsabeelahmed.expenses;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import dev.muhammadsabeelahmed.expenses.splash.SplashActivity;

public class Splash extends AppCompatActivity {
    PreferencesHandler preferencesHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        preferencesHandler = new PreferencesHandler(Splash.this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (preferencesHandler.getAppFirstTime().equals("true")) {
                    startActivity(new Intent(Splash.this, UserOnBoarding.class));
                    finish();
                } else {
                    User.username = preferencesHandler.getUname();
                    User.useremail = preferencesHandler.getUemail();
                    startActivity(new Intent(Splash.this, SplashActivity.class));
                    finish();
                }

            }
        }, 4000);
    }
}
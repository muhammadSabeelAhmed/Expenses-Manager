package dev.muhammadsabeelahmed.expenses.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import dev.muhammadsabeelahmed.expenses.Application
import dev.muhammadsabeelahmed.expenses.R
import dev.muhammadsabeelahmed.expenses.data.preference.PreferenceDataSource
import dev.muhammadsabeelahmed.expenses.home.presentation.HomeActivity
import dev.muhammadsabeelahmed.expenses.onboarding.OnboardingActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (isUserOnboarded()) {
            HomeActivity.start(this)
        } else {
            OnboardingActivity.start(this)
        }

        finish()
    }

    private fun isUserOnboarded(): Boolean =
        (application as Application).preferenceDataSource.getIsUserOnboarded(applicationContext)
}
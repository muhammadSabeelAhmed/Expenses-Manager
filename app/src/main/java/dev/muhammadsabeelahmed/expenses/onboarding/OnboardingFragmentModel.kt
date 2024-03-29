package dev.muhammadsabeelahmed.expenses.onboarding

import android.content.Intent
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.muhammadsabeelahmed.expenses.Application
import dev.muhammadsabeelahmed.expenses.authentication.AuthenticationManager
import dev.muhammadsabeelahmed.expenses.data.preference.PreferenceDataSource
import dev.muhammadsabeelahmed.expenses.util.extensions.plusAssign
import dev.muhammadsabeelahmed.expenses.util.reactive.DataEvent
import dev.muhammadsabeelahmed.expenses.util.reactive.Event
import dev.muhammadsabeelahmed.expenses.util.reactive.Variable
import io.reactivex.disposables.CompositeDisposable

class OnboardingFragmentModel(
    application: Application,
    private val authenticationManager: AuthenticationManager,
    private val preferenceDataSource: PreferenceDataSource
) : AndroidViewModel(application) {

    val isLoading = Variable(false)

    val requestGoogleSignIn = DataEvent<Intent>()
    val navigateToHome = Event()

    private val disposables = CompositeDisposable()

    fun continueWithoutSigningInRequested() {
        finishOnboardingAndNavigateHome()
    }

    fun continueWithGoogleRequested() {
        requestGoogleSignIn.next(authenticationManager.getGoogleSignInRequest())
    }

    fun handleGoogleSignInResult(result: Intent) {
        isLoading.value = true

        disposables += authenticationManager.handleGoogleSignInResult(result)
            .subscribe({
                Log.d(TAG, "Succeeded to sign in with Google.")

                isLoading.value = false

                DataMigrationWorker.enqueue(getApplication())
                finishOnboardingAndNavigateHome()
            }, { error ->
                isLoading.value = false

                Log.w(TAG, "Failed to sign in with Google, cause: ($error).")
            })
    }

    private fun finishOnboardingAndNavigateHome() {
        preferenceDataSource.setIsUserOnboarded(getApplication(), true)
        navigateToHome.next()
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val application: Application) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return OnboardingFragmentModel(
                application,
                application.authenticationManager,
                application.preferenceDataSource
            ) as T
        }
    }

    companion object {
        private const val TAG = "OnboardingFragmentModel"
    }
}
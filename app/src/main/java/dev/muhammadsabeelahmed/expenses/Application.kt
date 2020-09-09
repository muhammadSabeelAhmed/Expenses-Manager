package dev.muhammadsabeelahmed.expenses

import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.jakewharton.threetenabp.AndroidThreeTen
import dev.muhammadsabeelahmed.expenses.authentication.AuthenticationManager
import dev.muhammadsabeelahmed.expenses.configuration.Configuration
import dev.muhammadsabeelahmed.expenses.configuration.FirebaseConfiguration
import dev.muhammadsabeelahmed.expenses.data.firebase.FirebaseDataStore
import dev.muhammadsabeelahmed.expenses.data.preference.PreferenceDataSource
import dev.muhammadsabeelahmed.expenses.data.room.ApplicationDatabase
import dev.muhammadsabeelahmed.expenses.data.room.RoomDataStore
import dev.muhammadsabeelahmed.expenses.data.store.DataStore

class Application : android.app.Application() {

    /* Singletons - should be replaced with some dependency injection tool. */

    val authenticationManager: AuthenticationManager by lazy {
        AuthenticationManager(this, FirebaseAuth.getInstance())
    }

    val defaultDataStore: DataStore
        get() {
            return if (authenticationManager.isUserSignedIn()) {
                cloudDataStore
            } else {
                localDataStore
            }
        }

    val localDataStore: DataStore by lazy {
        val database = ApplicationDatabase.build(this)

        RoomDataStore(
            database.expenseDao(),
            database.tagDao(),
            database.expenseTagJoinDao()
        )
    }

    val cloudDataStore: DataStore by lazy {
        FirebaseDataStore(
            FirebaseAuth.getInstance(),
            FirebaseFirestore.getInstance()
        )
    }

    val preferenceDataSource: PreferenceDataSource by lazy {
        PreferenceDataSource()
    }

    val configuration: Configuration by lazy {
        FirebaseConfiguration(FirebaseRemoteConfig.getInstance())
    }

    override fun onCreate() {
        super.onCreate()
        initializeThreeTeen()
        enqueueConfigurationSync()
        applyTheme()
    }

    private fun initializeThreeTeen() {
        AndroidThreeTen.init(this)
    }

    private fun enqueueConfigurationSync() {
        configuration.enqueueSync()
    }

    private fun applyTheme() {
        val theme = preferenceDataSource.getTheme(applicationContext)
        AppCompatDelegate.setDefaultNightMode(theme.toNightMode())
    }
}

package dev.muhammadsabeelahmed.expenses.about

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.muhammadsabeelahmed.expenses.Application

class AboutActivityModel(application: Application) : AndroidViewModel(application) {

    @Suppress("UNCHECKED_CAST")
    class Factory(private val application: Application) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return AboutActivityModel(application) as T
        }
    }
}
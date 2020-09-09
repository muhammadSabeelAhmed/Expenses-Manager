package dev.muhammadsabeelahmed.expenses.addeditexpense.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.muhammadsabeelahmed.expenses.data.model.Tag

class AddEditExpenseActivityModel : ViewModel() {

    val selectedTags = MutableLiveData<List<Tag>>()

    fun selectTags(tags: List<Tag>) {
        selectedTags.value = tags
    }
}
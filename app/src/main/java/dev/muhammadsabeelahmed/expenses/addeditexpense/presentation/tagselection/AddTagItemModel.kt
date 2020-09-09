package dev.muhammadsabeelahmed.expenses.addeditexpense.presentation.tagselection

class AddTagItemModel :
    TagSelectionItemModel {

    var click: (() -> Unit)? = null
}
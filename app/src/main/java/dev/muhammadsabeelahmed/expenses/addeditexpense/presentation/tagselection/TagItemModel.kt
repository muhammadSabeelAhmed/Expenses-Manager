package dev.muhammadsabeelahmed.expenses.addeditexpense.presentation.tagselection

import dev.muhammadsabeelahmed.expenses.data.model.Tag

class TagItemModel(val tag: Tag):
    TagSelectionItemModel {

    var isChecked = false
    val name = tag.name

    var checkClick: (() -> Unit)? = null
    var deleteClick: (() -> Unit)? = null
}
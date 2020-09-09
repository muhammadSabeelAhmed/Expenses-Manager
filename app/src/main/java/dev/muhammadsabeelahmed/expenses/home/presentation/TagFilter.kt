package dev.muhammadsabeelahmed.expenses.home.presentation

import android.os.Parcelable
import dev.muhammadsabeelahmed.expenses.data.model.Tag
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TagFilter(val tags: MutableSet<Tag> = HashSet()) : Parcelable {

    val isEmpty: Boolean
        get() = tags.isEmpty()

    fun add(tag: Tag) = tags.add(tag)

    fun remove(tag: Tag) = tags.remove(tag)

    fun accepts(tags: List<Tag>) = tags.containsAll(this.tags)
}
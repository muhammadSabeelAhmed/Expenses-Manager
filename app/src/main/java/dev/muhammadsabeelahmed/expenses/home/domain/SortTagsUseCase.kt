package dev.muhammadsabeelahmed.expenses.home.domain

import dev.muhammadsabeelahmed.expenses.data.model.Tag

class SortTagsUseCase {
    operator fun invoke(tags: List<Tag>): List<Tag> {
        return tags.sortedBy { it.name }
    }
}
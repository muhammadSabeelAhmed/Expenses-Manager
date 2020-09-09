package dev.muhammadsabeelahmed.expenses.home.domain

import dev.muhammadsabeelahmed.expenses.data.model.Expense
import dev.muhammadsabeelahmed.expenses.home.presentation.DateRange
import dev.muhammadsabeelahmed.expenses.home.presentation.TagFilter

class FilterExpensesUseCase {

    operator fun invoke(
        expenses: List<Expense>,
        dateRange: DateRange,
        tagFilter: TagFilter?
    ): List<Expense> {
        return expenses.filter { expense ->
            dateRange.contains(expense.date) &&
                    tagFilter?.let { expense.tags.containsAll(it.tags) } ?: true
        }
    }
}
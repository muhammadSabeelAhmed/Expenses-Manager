package dev.muhammadsabeelahmed.expenses.expensedetail.domain

import dev.muhammadsabeelahmed.expenses.data.model.Expense
import dev.muhammadsabeelahmed.expenses.data.store.DataStore
import io.reactivex.Completable

class DeleteExpenseUseCase(private val dataStore: DataStore) {

    operator fun invoke(expense: Expense): Completable {
        return dataStore.deleteExpense(expense)
    }
}
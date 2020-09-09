package dev.muhammadsabeelahmed.expenses.expensedetail.domain

import dev.muhammadsabeelahmed.expenses.data.model.Expense
import dev.muhammadsabeelahmed.expenses.data.store.DataStore
import io.reactivex.Observable

class ObserveExpenseUseCase(private val dataStore: DataStore) {
    operator fun invoke(expenseId: String): Observable<Expense> {
        return dataStore.observeExpense(expenseId)
    }
}
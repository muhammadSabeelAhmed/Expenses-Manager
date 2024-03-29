package dev.muhammadsabeelahmed.expenses.data.room

import dev.muhammadsabeelahmed.expenses.data.model.Expense
import dev.muhammadsabeelahmed.expenses.data.model.Tag
import dev.muhammadsabeelahmed.expenses.data.room.dao.ExpenseDao
import dev.muhammadsabeelahmed.expenses.data.room.dao.ExpenseTagJoinDao
import dev.muhammadsabeelahmed.expenses.data.room.dao.TagDao
import dev.muhammadsabeelahmed.expenses.data.room.entities.ExpenseEntity
import dev.muhammadsabeelahmed.expenses.data.room.entities.ExpenseTagJoinEntity
import dev.muhammadsabeelahmed.expenses.data.room.entities.TagEntity
import dev.muhammadsabeelahmed.expenses.data.store.DataStore
import dev.muhammadsabeelahmed.expenses.util.reactive.combineLatest
import dev.muhammadsabeelahmed.expenses.util.reactive.zip
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction

class RoomDataStore(
    private val expenseDao: ExpenseDao,
    private val tagDao: TagDao,
    private val expenseTagJoinDao: ExpenseTagJoinDao
) : DataStore {

    // Expenses

    override fun observeExpenses(): Observable<List<Expense>> {
        return expenseDao.observeAll()
            .switchMap { makeExpensesObservable(it) }
    }

    private fun makeExpensesObservable(
        expenseEntities: List<ExpenseEntity>
    ): Observable<List<Expense>> {
        if (expenseEntities.isEmpty()) {
            return Observable.just(emptyList())
        }

        val observables = expenseEntities.map { expenseEntity ->
            expenseTagJoinDao
                .observeTagsByExpenseId(expenseEntity.id)
                .map { tagEntities -> expenseEntity.mapToExpense(tagEntities) }
        }

        return observables.combineLatest()
    }

    override fun getExpenses(): Single<List<Expense>> {
        return expenseDao.getAll()
            .flatMap { makeExpensesSingle(it) }
    }

    private fun makeExpensesSingle(
        expenseEntities: List<ExpenseEntity>
    ): Single<List<Expense>> {
        if (expenseEntities.isEmpty()) {
            return Single.just(emptyList())
        }

        val singles = expenseEntities.map { expenseEntity ->
            expenseTagJoinDao
                .getTagsByExpenseId(expenseEntity.id)
                .map { tagEntities -> expenseEntity.mapToExpense(tagEntities) }
        }

        return singles.zip()
    }

    override fun observeExpense(id: String): Observable<Expense> {
        val expenseEntityObservable =
            expenseDao.observeById(id.toLong())
        val tagEntitiesObservable =
            expenseTagJoinDao.observeTagsByExpenseId(id.toLong())

        return Observable.combineLatest(
            expenseEntityObservable,
            tagEntitiesObservable,
            BiFunction { expenseEntity, tagEntities -> expenseEntity.mapToExpense(tagEntities) }
        )
    }

    override fun getExpense(id: String): Single<Expense> {
        val expenseEntitySingle =
            expenseDao.getById(id.toLong())
        val tagEntitiesSingle =
            expenseTagJoinDao.getTagsByExpenseId(id.toLong())

        return Single.zip(
            expenseEntitySingle,
            tagEntitiesSingle,
            BiFunction { expenseEntity, tagEntities -> expenseEntity.mapToExpense(tagEntities) }
        )
    }

    override fun insertExpense(expense: Expense): Single<String> {
        return Single.fromCallable { ExpenseEntity.prepareForInsertion(expense) }
            .flatMap { expenseDao.insert(it) }
            .map { id ->
                for (tag in expense.tags) {
                    val join = ExpenseTagJoinEntity(id, TagEntity.fromTag(tag).id)
                    expenseTagJoinDao.insert(join)
                }

                id.toString()
            }
    }

    override fun updateExpense(expense: Expense): Completable {
        return Single.fromCallable { ExpenseEntity.prepareForUpdate(expense) }
            .flatMap { expenseDao.update(it).toSingleDefault(it.id) }
            .map { id ->
                expenseTagJoinDao.deleteByExpenseId(id)

                for (tag in expense.tags) {
                    val join = ExpenseTagJoinEntity(id, TagEntity.fromTag(tag).id)
                    expenseTagJoinDao.insert(join)
                }
            }
            .ignoreElement()
    }

    override fun deleteExpense(expense: Expense): Completable {
        return expenseDao.deleteById(ExpenseEntity.fromExpense(expense).id)
    }

    override fun deleteAllExpenses(): Completable {
        return expenseDao.deleteAll()
    }

    // Tags

    override fun observeTags(): Observable<List<Tag>> {
        return tagDao.observeAll()
            .map { tagEntities -> tagEntities.map { it.mapToTag() } }
    }

    override fun getTags(): Single<List<Tag>> {
        return tagDao.getAll()
            .map { tagEntities -> tagEntities.map { it.mapToTag() } }
    }

    override fun insertTag(tag: Tag): Single<String> {
        return Single.fromCallable { TagEntity.prepareForInsertion(tag) }
            .flatMap { tagDao.insert(it) }
            .map { it.toString() }
    }

    override fun deleteTag(tag: Tag): Completable {
        return tagDao.deleteById(TagEntity.fromTag(tag).id)
    }

    override fun deleteAllTags(): Completable {
        return tagDao.deleteAll()
    }
}
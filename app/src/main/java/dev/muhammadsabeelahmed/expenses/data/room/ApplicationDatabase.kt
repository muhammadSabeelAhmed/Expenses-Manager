package dev.muhammadsabeelahmed.expenses.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import dev.muhammadsabeelahmed.expenses.data.room.converter.CurrencyConverter
import dev.muhammadsabeelahmed.expenses.data.room.converter.LocalDateConverter
import dev.muhammadsabeelahmed.expenses.data.room.dao.ExpenseDao
import dev.muhammadsabeelahmed.expenses.data.room.dao.ExpenseTagJoinDao
import dev.muhammadsabeelahmed.expenses.data.room.dao.TagDao
import dev.muhammadsabeelahmed.expenses.data.room.entities.ExpenseEntity
import dev.muhammadsabeelahmed.expenses.data.room.entities.ExpenseTagJoinEntity
import dev.muhammadsabeelahmed.expenses.data.room.entities.TagEntity

@Database(
    entities = [
        ExpenseEntity::class,
        ExpenseTagJoinEntity::class,
        TagEntity::class
    ],
    version = 2,
    exportSchema = true
)
@TypeConverters(
    value = [
        CurrencyConverter::class,
        LocalDateConverter::class
    ]
)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun expenseDao(): ExpenseDao

    abstract fun tagDao(): TagDao

    abstract fun expenseTagJoinDao(): ExpenseTagJoinDao

    companion object {

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "ALTER TABLE expenses ADD COLUMN created_at INTEGER NOT NULL DEFAULT 0"
                )
                database.execSQL(
                    "ALTER TABLE expenses ADD COLUMN modified_at INTEGER NOT NULL DEFAULT 0"
                )
            }
        }

        private const val DATABASE_NAME = "database"

        fun build(context: Context) =
            Room.databaseBuilder(context, ApplicationDatabase::class.java,
                DATABASE_NAME
            )
                .addMigrations(MIGRATION_1_2)
                .fallbackToDestructiveMigration()
                .build()
    }
}
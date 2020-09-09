package dev.muhammadsabeelahmed.expenses.data.room.converter

import androidx.room.TypeConverter
import dev.muhammadsabeelahmed.expenses.data.model.Currency

class CurrencyConverter {

    companion object {

        @JvmStatic
        @TypeConverter
        fun toCurrency(string: String) = Currency.fromCode(string)

        @JvmStatic
        @TypeConverter
        fun toString(currency: Currency) = currency.toString()
    }
}
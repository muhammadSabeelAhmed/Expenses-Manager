package dev.muhammadsabeelahmed.expenses.util.extensions

import android.content.Context
import dev.muhammadsabeelahmed.expenses.Application

val Context.application: Application
    get() = applicationContext as Application
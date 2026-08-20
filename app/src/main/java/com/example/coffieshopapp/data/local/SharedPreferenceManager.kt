package com.example.coffieshopapp.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object SharedPreferenceManager {

    private const val PREF_NAME = "CoffeeAppPrefs"

    private const val KEY_NAME = "name"
    private const val KEY_EMAIL = "email"



    private fun getPref(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveUserData(
        context: Context,
        name: String,
        email: String
    ) {
        getPref(context).edit {
            putString(KEY_NAME, name)
            putString(KEY_EMAIL, email)
        }
    }

    fun getUserName(context: Context): String? {
        return getPref(context).getString(KEY_NAME, null)
    }

    fun getUserEmail(context: Context): String? {
        return getPref(context).getString(KEY_EMAIL, null)
    }

    fun clearUserData(context: Context) {
        getPref(context).edit { clear() }
    }
}
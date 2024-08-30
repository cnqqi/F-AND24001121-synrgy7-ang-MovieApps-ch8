package com.synrgy.ch7.data.local

import android.content.SharedPreferences
import javax.inject.Inject

class PreferencesManager @Inject constructor(
    private val sharedPreferences: SharedPreferences) {

    companion object {
        private const val KEY_USERNAME = "username"
    }

    fun saveUsername(username: String) {
        sharedPreferences.edit().putString(KEY_USERNAME, username).apply()
    }


    fun getUsername(): String {
        return sharedPreferences.getString("username", "") ?: ""
    }
}

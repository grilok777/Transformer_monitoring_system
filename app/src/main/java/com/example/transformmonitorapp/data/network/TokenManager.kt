package com.example.transformmonitorapp.data.network

import android.content.SharedPreferences
import androidx.core.content.edit

class TokenManager(private val prefs: SharedPreferences) {

    fun saveTokens(access: String, refresh: String) {
        prefs.edit {
            putString("ACCESS_TOKEN", access)
            putString("REFRESH_TOKEN", refresh)
        }
    }

    fun getAccessToken(): String? = prefs.getString("ACCESS_TOKEN", null)

    fun getRefreshToken(): String? = prefs.getString("REFRESH_TOKEN", null)

    fun clear() {
        prefs.edit { clear() }
    }
}
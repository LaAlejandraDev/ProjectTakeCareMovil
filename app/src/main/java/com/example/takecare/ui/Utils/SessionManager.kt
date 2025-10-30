package com.example.takecare.ui.Utils

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore("user_session")
class SessionManager(private val context: Context) {

    companion object {
        private val KEY_TOKEN = stringPreferencesKey("token")
        private val KEY_USER_ID = intPreferencesKey("userId")
        private val KEY_NAME = stringPreferencesKey("userName")
        private val KEY_EMAIL = stringPreferencesKey("email")
    }

    suspend fun saveSession(token: String, userId: Int, userName: String, email: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_TOKEN] = token
            prefs[KEY_USER_ID] = userId
            prefs[KEY_NAME] = userName
            prefs[KEY_EMAIL] = email
        }
    }

    fun getToken(): Flow<String?> = context.dataStore.data.map { it[KEY_TOKEN] }
    fun getUserId(): Flow<Int?> = context.dataStore.data.map { it[KEY_USER_ID] }
    fun getUserName(): Flow<String?> = context.dataStore.data.map { it[KEY_NAME] }
    fun getEmail(): Flow<String?> = context.dataStore.data.map { it[KEY_EMAIL] }

    suspend fun clearSession() {
        context.dataStore.edit { it.clear() }
    }
}
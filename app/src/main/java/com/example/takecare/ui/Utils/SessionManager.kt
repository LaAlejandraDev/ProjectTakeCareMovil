package com.example.takecare.ui.Utils

import com.example.takecare.data.models.UserSession
import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore("user_session")

class SessionManager (private val context: Context) {
    companion object {
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val USER_ID_KEY = intPreferencesKey("user_id")
        private val NAME_KEY = stringPreferencesKey("name")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val ROLE_KEY = intPreferencesKey("role")
    }

    suspend fun saveSession(user: UserSession) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = user.token
            preferences[USER_ID_KEY] = user.userId
            preferences[NAME_KEY] = user.userName
            preferences[EMAIL_KEY] = user.email
            preferences[ROLE_KEY] = user.role
        }
    }

    fun getSession(): Flow<UserSession> = context.dataStore.data.map { prefs ->
        UserSession(
            token = prefs[TOKEN_KEY] ?: "",
            userId = prefs[USER_ID_KEY] ?: -1,
            userName = prefs[NAME_KEY] ?: "",
            email = prefs[EMAIL_KEY] ?: "",
            role = prefs[ROLE_KEY] ?: 0
        )
    }

    suspend fun clearSession() {
        context.dataStore.edit { it.clear() }
    }
}
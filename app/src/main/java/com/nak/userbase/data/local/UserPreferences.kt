package com.nak.userbase.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.userDataStore by preferencesDataStore(
    name = "user_prefs"
)

object PreferencesKeys {
    val USER_ID = stringPreferencesKey("user_id")
}

class UserPreferencesManager(
    private val context: Context
) {
    val userIdFlow: Flow<String?> = context.userDataStore.data.map { preferences ->
        preferences[PreferencesKeys.USER_ID]
    }

    suspend fun saveUserId(userId: String) {
        context.userDataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_ID] = userId
        }
    }

    suspend fun getUserId(): String? {
        val preferences = context.userDataStore.data.first()
        return preferences[PreferencesKeys.USER_ID]
    }
}

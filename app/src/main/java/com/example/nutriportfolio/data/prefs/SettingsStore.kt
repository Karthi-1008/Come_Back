package com.example.nutriportfolio.data.prefs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.nutriportfolio.ui.theme.ThemeMode
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_settings")

@Singleton
class SettingsStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val themeModeKey = stringPreferencesKey("theme_mode")
    private val signedInKey = booleanPreferencesKey("signed_in")
    private val userNameKey = stringPreferencesKey("user_name")
    private val userEmailKey = stringPreferencesKey("user_email")

    val themeModeFlow: Flow<ThemeMode> = context.dataStore.data.map { preferences ->
        val raw = preferences[themeModeKey] ?: ThemeMode.SYSTEM.name
        try {
            ThemeMode.valueOf(raw)
        } catch (_: Exception) {
            ThemeMode.SYSTEM
        }
    }

    val isSignedInFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[signedInKey] ?: false
    }

    val userNameFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[userNameKey]
    }

    val userEmailFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[userEmailKey]
    }

    suspend fun setThemeMode(mode: ThemeMode) {
        context.dataStore.edit { preferences ->
            preferences[themeModeKey] = mode.name
        }
    }

    suspend fun setSignedIn(signedIn: Boolean, name: String? = null, email: String? = null) {
        context.dataStore.edit { preferences ->
            preferences[signedInKey] = signedIn
            if (name != null) {
                preferences[userNameKey] = name
            } else if (!signedIn) {
                preferences.remove(userNameKey)
            }
            if (email != null) {
                preferences[userEmailKey] = email
            } else if (!signedIn) {
                preferences.remove(userEmailKey)
            }
        }
    }

    suspend fun clearAccount() {
        context.dataStore.edit { preferences ->
            preferences[signedInKey] = false
            preferences.remove(userNameKey)
            preferences.remove(userEmailKey)
        }
    }
}

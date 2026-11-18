package com.example.nutriportfolio.ui.settings

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutriportfolio.data.prefs.SettingsStore
import com.example.nutriportfolio.data.repo.AuthRepository
import com.example.nutriportfolio.data.repo.ProfileRepository
import com.example.nutriportfolio.domain.model.UserProfile
import com.example.nutriportfolio.ui.theme.ThemeMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val authRepository: AuthRepository,
    private val settingsStore: SettingsStore
) : ViewModel() {

    val profile: StateFlow<UserProfile?> = profileRepository.profileFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val themeMode: StateFlow<ThemeMode> = settingsStore.themeModeFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ThemeMode.SYSTEM)

    val userName: StateFlow<String?> = authRepository.userNameFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val userEmail: StateFlow<String?> = authRepository.userEmailFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun setThemeMode(mode: ThemeMode) {
        viewModelScope.launch {
            settingsStore.setThemeMode(mode)
        }
    }

    fun setLanguage(languageTag: String) {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(languageTag))
    }

    fun getCurrentLanguageTag(): String {
        val currentLocales = AppCompatDelegate.getApplicationLocales()
        if (!currentLocales.isEmpty) {
            val tag = currentLocales.toLanguageTags().split(",").firstOrNull() ?: "en"
            return if (tag.startsWith("ta")) "ta"
            else if (tag.startsWith("te")) "te"
            else if (tag.startsWith("ml")) "ml"
            else if (tag.startsWith("hi")) "hi"
            else "en"
        }
        return "en"
    }

    fun updateProfile(profile: UserProfile, onComplete: () -> Unit) {
        viewModelScope.launch {
            val updated = profile.copy(updatedAt = System.currentTimeMillis())
            profileRepository.saveProfile(updated)
            onComplete()
        }
    }

    fun signOut(context: Context, onSignedOut: () -> Unit) {
        viewModelScope.launch {
            profileRepository.clearProfile()
            authRepository.signOut(context)
            onSignedOut()
        }
    }
}

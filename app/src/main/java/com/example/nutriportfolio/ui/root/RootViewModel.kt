package com.example.nutriportfolio.ui.root

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
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed interface AppStartupState {
    data object Loading : AppStartupState
    data object NeedsSignIn : AppStartupState
    data object NeedsSurvey : AppStartupState
    data class Ready(val profile: UserProfile) : AppStartupState
}

@HiltViewModel
class RootViewModel @Inject constructor(
    authRepository: AuthRepository,
    profileRepository: ProfileRepository,
    settingsStore: SettingsStore
) : ViewModel() {

    val themeMode: StateFlow<ThemeMode> = settingsStore.themeModeFlow
        .stateIn(viewModelScope, SharingStarted.Eagerly, ThemeMode.SYSTEM)

    val startupState: StateFlow<AppStartupState> = combine(
        authRepository.isSignedInFlow,
        profileRepository.profileFlow
    ) { isSignedIn, profile ->
        when {
            !isSignedIn -> AppStartupState.NeedsSignIn
            profile == null -> AppStartupState.NeedsSurvey
            else -> AppStartupState.Ready(profile)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AppStartupState.Loading)
}

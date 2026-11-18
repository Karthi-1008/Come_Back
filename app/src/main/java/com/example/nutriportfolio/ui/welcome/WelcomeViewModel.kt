package com.example.nutriportfolio.ui.welcome

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutriportfolio.data.repo.AuthRepository
import com.example.nutriportfolio.data.repo.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class WelcomeUiState(
    val isLoading: Boolean = false,
    val errorMessageResId: Int? = null
)

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(WelcomeUiState())
    val uiState: StateFlow<WelcomeUiState> = _uiState.asStateFlow()

    fun signIn(activity: Activity) {
        if (_uiState.value.isLoading) return

        _uiState.update { it.copy(isLoading = true, errorMessageResId = null) }
        viewModelScope.launch {
            when (val result = authRepository.signIn(activity)) {
                is AuthResult.Success -> {
                    _uiState.update { it.copy(isLoading = false, errorMessageResId = null) }
                }
                is AuthResult.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessageResId = result.messageResId
                        )
                    }
                }
            }
        }
    }

    fun dismissError() {
        _uiState.update { it.copy(errorMessageResId = null) }
    }
}

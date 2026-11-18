package com.example.nutriportfolio.ui.survey

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutriportfolio.data.prefs.SettingsStore
import com.example.nutriportfolio.data.repo.ProfileRepository
import com.example.nutriportfolio.domain.model.ActivityLevel
import com.example.nutriportfolio.domain.model.DietType
import com.example.nutriportfolio.domain.model.Sex
import com.example.nutriportfolio.domain.model.UserProfile
import com.example.nutriportfolio.domain.validation.SurveyValidation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SurveyUiState(
    val currentStep: Int = 1,
    val totalSteps: Int = 6,
    val age: Int = SurveyValidation.DEFAULT_AGE,
    val sex: Sex? = null,
    val heightCm: Float = SurveyValidation.DEFAULT_HEIGHT_CM,
    val isFtInMode: Boolean = false,
    val weightKg: Float = SurveyValidation.DEFAULT_WEIGHT_KG,
    val dietType: DietType? = null,
    val activityLevel: ActivityLevel? = null,
    val isNextEnabled: Boolean = true,
    val isSaving: Boolean = false
)

@HiltViewModel
class SurveyViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val profileRepository: ProfileRepository,
    private val settingsStore: SettingsStore
) : ViewModel() {

    private val stepKey = "survey_step"
    private val ageKey = "survey_age"
    private val sexKey = "survey_sex"
    private val heightKey = "survey_height"
    private val isFtInKey = "survey_is_ft_in"
    private val weightKey = "survey_weight"
    private val dietKey = "survey_diet"
    private val activityKey = "survey_activity"

    private val initialAge: Int = savedStateHandle[ageKey] ?: SurveyValidation.DEFAULT_AGE
    private val initialSex: Sex? = savedStateHandle.get<String>(sexKey)?.let { runCatching { Sex.valueOf(it) }.getOrNull() }
    private val initialHeight: Float = savedStateHandle[heightKey] ?: SurveyValidation.DEFAULT_HEIGHT_CM
    private val initialFtIn: Boolean = savedStateHandle[isFtInKey] ?: false
    private val initialWeight: Float = savedStateHandle[weightKey] ?: SurveyValidation.DEFAULT_WEIGHT_KG
    private val initialDiet: DietType? = savedStateHandle.get<String>(dietKey)?.let { runCatching { DietType.valueOf(it) }.getOrNull() }
    private val initialAct: ActivityLevel? = savedStateHandle.get<String>(activityKey)?.let { runCatching { ActivityLevel.valueOf(it) }.getOrNull() }
    private val initialStep: Int = savedStateHandle[stepKey] ?: 1

    private val _uiState = MutableStateFlow(
        SurveyUiState(
            currentStep = initialStep,
            age = initialAge,
            sex = initialSex,
            heightCm = initialHeight,
            isFtInMode = initialFtIn,
            weightKg = initialWeight,
            dietType = initialDiet,
            activityLevel = initialAct,
            isNextEnabled = SurveyValidation.isStepValid(
                step = initialStep,
                age = initialAge,
                sex = initialSex,
                heightCm = initialHeight,
                weightKg = initialWeight,
                dietType = initialDiet,
                activityLevel = initialAct
            )
        )
    )
    val uiState: StateFlow<SurveyUiState> = _uiState.asStateFlow()

    private fun recomputeValidity(state: SurveyUiState): Boolean {
        return SurveyValidation.isStepValid(
            step = state.currentStep,
            age = state.age,
            sex = state.sex,
            heightCm = state.heightCm,
            weightKg = state.weightKg,
            dietType = state.dietType,
            activityLevel = state.activityLevel
        )
    }

    fun setAge(newAge: Int) {
        if (SurveyValidation.isAgeValid(newAge)) {
            savedStateHandle[ageKey] = newAge
            _uiState.update { current ->
                val next = current.copy(age = newAge)
                next.copy(isNextEnabled = recomputeValidity(next))
            }
        }
    }

    fun setSex(newSex: Sex) {
        savedStateHandle[sexKey] = newSex.name
        _uiState.update { current ->
            val next = current.copy(sex = newSex)
            next.copy(isNextEnabled = recomputeValidity(next))
        }
    }

    fun setHeightCm(newHeightCm: Float) {
        val clamped = (newHeightCm.coerceIn(
            SurveyValidation.MIN_HEIGHT_CM,
            SurveyValidation.MAX_HEIGHT_CM
        ) * 10f).toInt() / 10f
        savedStateHandle[heightKey] = clamped
        _uiState.update { current ->
            val next = current.copy(heightCm = clamped)
            next.copy(isNextEnabled = recomputeValidity(next))
        }
    }

    fun setFtInMode(enabled: Boolean) {
        savedStateHandle[isFtInKey] = enabled
        _uiState.update { it.copy(isFtInMode = enabled) }
    }

    fun setWeightKg(newWeightKg: Float) {
        val clamped = (newWeightKg.coerceIn(
            SurveyValidation.MIN_WEIGHT_KG,
            SurveyValidation.MAX_WEIGHT_KG
        ) * 10f).toInt() / 10f
        savedStateHandle[weightKey] = clamped
        _uiState.update { current ->
            val next = current.copy(weightKg = clamped)
            next.copy(isNextEnabled = recomputeValidity(next))
        }
    }

    fun setDietType(diet: DietType) {
        savedStateHandle[dietKey] = diet.name
        _uiState.update { current ->
            val next = current.copy(dietType = diet)
            next.copy(isNextEnabled = recomputeValidity(next))
        }
    }

    fun setActivityLevel(activity: ActivityLevel) {
        savedStateHandle[activityKey] = activity.name
        _uiState.update { current ->
            val next = current.copy(activityLevel = activity)
            next.copy(isNextEnabled = recomputeValidity(next))
        }
    }

    fun nextStep() {
        val current = _uiState.value
        if (current.currentStep < current.totalSteps && current.isNextEnabled) {
            val nextStep = current.currentStep + 1
            savedStateHandle[stepKey] = nextStep
            _uiState.update {
                val next = it.copy(currentStep = nextStep)
                next.copy(isNextEnabled = recomputeValidity(next))
            }
        }
    }

    fun previousStep(): Boolean {
        val current = _uiState.value
        return if (current.currentStep > 1) {
            val prevStep = current.currentStep - 1
            savedStateHandle[stepKey] = prevStep
            _uiState.update {
                val next = it.copy(currentStep = prevStep)
                next.copy(isNextEnabled = recomputeValidity(next))
            }
            true
        } else {
            false
        }
    }

    fun finishSurvey(onComplete: () -> Unit) {
        val state = _uiState.value
        val sexVal = state.sex ?: return
        val dietVal = state.dietType ?: return
        val actVal = state.activityLevel ?: return

        if (!SurveyValidation.isFullProfileValid(
                state.age,
                sexVal,
                state.heightCm,
                state.weightKg,
                dietVal,
                actVal
            )
        ) {
            return
        }

        _uiState.update { it.copy(isSaving = true) }

        viewModelScope.launch {
            val now = System.currentTimeMillis()
            val userName = settingsStore.userNameFlow.firstOrNull()
            val userEmail = settingsStore.userEmailFlow.firstOrNull()

            val profile = UserProfile(
                id = 1,
                displayName = userName,
                email = userEmail,
                ageYears = state.age,
                ageSetAt = now,
                sex = sexVal,
                heightCm = state.heightCm,
                weightKg = state.weightKg,
                dietType = dietVal,
                activityLevel = actVal,
                createdAt = now,
                updatedAt = now
            )

            profileRepository.saveProfile(profile)
            _uiState.update { it.copy(isSaving = false) }
            onComplete()
        }
    }
}

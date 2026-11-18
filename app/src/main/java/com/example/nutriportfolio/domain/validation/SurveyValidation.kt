package com.example.nutriportfolio.domain.validation

import com.example.nutriportfolio.domain.model.ActivityLevel
import com.example.nutriportfolio.domain.model.DietType
import com.example.nutriportfolio.domain.model.Sex

object SurveyValidation {
    const val MIN_AGE = 2
    const val MAX_AGE = 100
    const val DEFAULT_AGE = 25

    const val MIN_HEIGHT_CM = 50f
    const val MAX_HEIGHT_CM = 250f
    const val DEFAULT_HEIGHT_CM = 165f

    const val MIN_WEIGHT_KG = 10f
    const val MAX_WEIGHT_KG = 300f
    const val DEFAULT_WEIGHT_KG = 60f

    fun isAgeValid(age: Int): Boolean {
        return age in MIN_AGE..MAX_AGE
    }

    fun isSexValid(sex: Sex?): Boolean {
        return sex != null
    }

    fun isHeightValid(heightCm: Float): Boolean {
        return heightCm in MIN_HEIGHT_CM..MAX_HEIGHT_CM
    }

    fun isWeightValid(weightKg: Float): Boolean {
        return weightKg in MIN_WEIGHT_KG..MAX_WEIGHT_KG
    }

    fun isDietValid(dietType: DietType?): Boolean {
        return dietType != null
    }

    fun isActivityValid(activityLevel: ActivityLevel?): Boolean {
        return activityLevel != null
    }

    fun isStepValid(
        step: Int,
        age: Int,
        sex: Sex?,
        heightCm: Float,
        weightKg: Float,
        dietType: DietType?,
        activityLevel: ActivityLevel?
    ): Boolean {
        return when (step) {
            1 -> isAgeValid(age)
            2 -> isSexValid(sex)
            3 -> isHeightValid(heightCm)
            4 -> isWeightValid(weightKg)
            5 -> isDietValid(dietType)
            6 -> isActivityValid(activityLevel)
            else -> false
        }
    }

    fun isFullProfileValid(
        age: Int,
        sex: Sex?,
        heightCm: Float,
        weightKg: Float,
        dietType: DietType?,
        activityLevel: ActivityLevel?
    ): Boolean {
        return isAgeValid(age) &&
                isSexValid(sex) &&
                isHeightValid(heightCm) &&
                isWeightValid(weightKg) &&
                isDietValid(dietType) &&
                isActivityValid(activityLevel)
    }
}

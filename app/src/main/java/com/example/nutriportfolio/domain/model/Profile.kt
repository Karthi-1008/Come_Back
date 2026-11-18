package com.example.nutriportfolio.domain.model

enum class Sex {
    MALE, FEMALE
}

enum class DietType {
    VEG, NON_VEG, VEGAN
}

enum class ActivityLevel {
    SEDENTARY, LIGHT, MODERATE, VERY_ACTIVE
}

data class UserProfile(
    val id: Int = 1,
    val displayName: String?,
    val email: String?,
    val ageYears: Int,
    val ageSetAt: Long,
    val sex: Sex,
    val heightCm: Float,
    val weightKg: Float,
    val dietType: DietType,
    val activityLevel: ActivityLevel,
    val createdAt: Long,
    val updatedAt: Long
)

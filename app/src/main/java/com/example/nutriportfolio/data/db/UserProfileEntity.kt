package com.example.nutriportfolio.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nutriportfolio.domain.model.ActivityLevel
import com.example.nutriportfolio.domain.model.DietType
import com.example.nutriportfolio.domain.model.Sex
import com.example.nutriportfolio.domain.model.UserProfile

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val id: Int = 1,
    val displayName: String?,
    val email: String?,
    val ageYears: Int,
    val ageSetAt: Long,
    val sex: String,
    val heightCm: Float,
    val weightKg: Float,
    val dietType: String,
    val activityLevel: String,
    val createdAt: Long,
    val updatedAt: Long
)

fun UserProfileEntity.toDomain(): UserProfile {
    return UserProfile(
        id = id,
        displayName = displayName,
        email = email,
        ageYears = ageYears,
        ageSetAt = ageSetAt,
        sex = try { Sex.valueOf(sex) } catch (_: Exception) { Sex.MALE },
        heightCm = heightCm,
        weightKg = weightKg,
        dietType = try { DietType.valueOf(dietType) } catch (_: Exception) { DietType.VEG },
        activityLevel = try { ActivityLevel.valueOf(activityLevel) } catch (_: Exception) { ActivityLevel.SEDENTARY },
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun UserProfile.toEntity(): UserProfileEntity {
    return UserProfileEntity(
        id = id,
        displayName = displayName,
        email = email,
        ageYears = ageYears,
        ageSetAt = ageSetAt,
        sex = sex.name,
        heightCm = heightCm,
        weightKg = weightKg,
        dietType = dietType.name,
        activityLevel = activityLevel.name,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

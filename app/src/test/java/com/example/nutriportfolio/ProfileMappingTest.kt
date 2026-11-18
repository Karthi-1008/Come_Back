package com.example.nutriportfolio

import com.example.nutriportfolio.data.db.UserProfileEntity
import com.example.nutriportfolio.data.db.toDomain
import com.example.nutriportfolio.data.db.toEntity
import com.example.nutriportfolio.domain.model.ActivityLevel
import com.example.nutriportfolio.domain.model.DietType
import com.example.nutriportfolio.domain.model.Sex
import com.example.nutriportfolio.domain.model.UserProfile
import org.junit.Assert.assertEquals
import org.junit.Test

class ProfileMappingTest {

    @Test
    fun entityToDomain_andBack() {
        val original = UserProfile(
            id = 1,
            displayName = "Karthik",
            email = "karthik@example.com",
            ageYears = 28,
            ageSetAt = 1700000000000L,
            sex = Sex.MALE,
            heightCm = 175.5f,
            weightKg = 72.0f,
            dietType = DietType.VEG,
            activityLevel = ActivityLevel.MODERATE,
            createdAt = 1700000000000L,
            updatedAt = 1700000050000L
        )

        val entity: UserProfileEntity = original.toEntity()

        assertEquals(1, entity.id)
        assertEquals("Karthik", entity.displayName)
        assertEquals("karthik@example.com", entity.email)
        assertEquals(28, entity.ageYears)
        assertEquals("MALE", entity.sex)
        assertEquals(175.5f, entity.heightCm, 0.01f)
        assertEquals(72.0f, entity.weightKg, 0.01f)
        assertEquals("VEG", entity.dietType)
        assertEquals("MODERATE", entity.activityLevel)

        val mappedBack = entity.toDomain()
        assertEquals(original, mappedBack)
    }

    @Test
    fun entityToDomain_fallbackForUnknownEnums() {
        val corruptedEntity = UserProfileEntity(
            id = 1,
            displayName = null,
            email = null,
            ageYears = 30,
            ageSetAt = 1000L,
            sex = "UNKNOWN",
            heightCm = 170f,
            weightKg = 65f,
            dietType = "FRUITARIAN",
            activityLevel = "EXTREME",
            createdAt = 1000L,
            updatedAt = 1000L
        )

        val domain = corruptedEntity.toDomain()
        assertEquals(Sex.MALE, domain.sex)
        assertEquals(DietType.VEG, domain.dietType)
        assertEquals(ActivityLevel.SEDENTARY, domain.activityLevel)
    }
}

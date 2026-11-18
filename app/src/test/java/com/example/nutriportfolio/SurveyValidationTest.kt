package com.example.nutriportfolio

import com.example.nutriportfolio.domain.model.ActivityLevel
import com.example.nutriportfolio.domain.model.DietType
import com.example.nutriportfolio.domain.model.Sex
import com.example.nutriportfolio.domain.validation.SurveyValidation
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class SurveyValidationTest {

    @Test
    fun ageValidation_edgesAndBounds() {
        // Range: 2 to 100
        assertFalse("Age 1 should be invalid", SurveyValidation.isAgeValid(1))
        assertTrue("Age 2 should be valid (min edge)", SurveyValidation.isAgeValid(2))
        assertTrue("Age 25 should be valid (normal)", SurveyValidation.isAgeValid(25))
        assertTrue("Age 100 should be valid (max edge)", SurveyValidation.isAgeValid(100))
        assertFalse("Age 101 should be invalid", SurveyValidation.isAgeValid(101))
        assertFalse("Negative age should be invalid", SurveyValidation.isAgeValid(-10))
    }

    @Test
    fun sexValidation_test() {
        assertTrue("Male should be valid", SurveyValidation.isSexValid(Sex.MALE))
        assertTrue("Female should be valid", SurveyValidation.isSexValid(Sex.FEMALE))
        assertFalse("Null sex should be invalid", SurveyValidation.isSexValid(null))
    }

    @Test
    fun heightValidation_edgesAndBounds() {
        // Range: 50cm to 250cm
        assertFalse("Height 49.9cm should be invalid", SurveyValidation.isHeightValid(49.9f))
        assertTrue("Height 50.0cm should be valid (min edge)", SurveyValidation.isHeightValid(50.0f))
        assertTrue("Height 165.0cm should be valid", SurveyValidation.isHeightValid(165.0f))
        assertTrue("Height 250.0cm should be valid (max edge)", SurveyValidation.isHeightValid(250.0f))
        assertFalse("Height 250.1cm should be invalid", SurveyValidation.isHeightValid(250.1f))
    }

    @Test
    fun weightValidation_edgesAndBounds() {
        // Range: 10kg to 300kg
        assertFalse("Weight 9.9kg should be invalid", SurveyValidation.isWeightValid(9.9f))
        assertTrue("Weight 10.0kg should be valid (min edge)", SurveyValidation.isWeightValid(10.0f))
        assertTrue("Weight 60.0kg should be valid", SurveyValidation.isWeightValid(60.0f))
        assertTrue("Weight 300.0kg should be valid (max edge)", SurveyValidation.isWeightValid(300.0f))
        assertFalse("Weight 300.1kg should be invalid", SurveyValidation.isWeightValid(300.1f))
    }

    @Test
    fun dietValidation_test() {
        assertTrue(SurveyValidation.isDietValid(DietType.VEG))
        assertTrue(SurveyValidation.isDietValid(DietType.NON_VEG))
        assertTrue(SurveyValidation.isDietValid(DietType.VEGAN))
        assertFalse(SurveyValidation.isDietValid(null))
    }

    @Test
    fun activityValidation_test() {
        assertTrue(SurveyValidation.isActivityValid(ActivityLevel.SEDENTARY))
        assertTrue(SurveyValidation.isActivityValid(ActivityLevel.LIGHT))
        assertTrue(SurveyValidation.isActivityValid(ActivityLevel.MODERATE))
        assertTrue(SurveyValidation.isActivityValid(ActivityLevel.VERY_ACTIVE))
        assertFalse(SurveyValidation.isActivityValid(null))
    }

    @Test
    fun isStepValid_checksEachStepIndependently() {
        // Step 1: Age
        assertTrue(SurveyValidation.isStepValid(1, 25, null, 0f, 0f, null, null))
        assertFalse(SurveyValidation.isStepValid(1, 1, null, 0f, 0f, null, null))

        // Step 2: Sex
        assertTrue(SurveyValidation.isStepValid(2, 25, Sex.FEMALE, 0f, 0f, null, null))
        assertFalse(SurveyValidation.isStepValid(2, 25, null, 0f, 0f, null, null))

        // Step 3: Height
        assertTrue(SurveyValidation.isStepValid(3, 25, null, 170f, 0f, null, null))
        assertFalse(SurveyValidation.isStepValid(3, 25, null, 30f, 0f, null, null))

        // Step 4: Weight
        assertTrue(SurveyValidation.isStepValid(4, 25, null, 0f, 65f, null, null))
        assertFalse(SurveyValidation.isStepValid(4, 25, null, 0f, 5f, null, null))

        // Step 5: Diet
        assertTrue(SurveyValidation.isStepValid(5, 25, null, 0f, 0f, DietType.VEGAN, null))
        assertFalse(SurveyValidation.isStepValid(5, 25, null, 0f, 0f, null, null))

        // Step 6: Activity
        assertTrue(SurveyValidation.isStepValid(6, 25, null, 0f, 0f, null, ActivityLevel.MODERATE))
        assertFalse(SurveyValidation.isStepValid(6, 25, null, 0f, 0f, null, null))
    }
}

package com.example.nutriportfolio

import com.example.nutriportfolio.domain.validation.HeightConversion
import org.junit.Assert.assertEquals
import org.junit.Test

class HeightConversionTest {

    @Test
    fun cmToFeetAndInches_standardValues() {
        // 182.88 cm -> 6 ft 0 in (72 in)
        val result6ft = HeightConversion.cmToFeetAndInches(182.88f)
        assertEquals(6, result6ft.first)
        assertEquals(0, result6ft.second)

        // 165 cm -> ~64.96 in -> round to 65 in -> 5 ft 5 in
        val result165 = HeightConversion.cmToFeetAndInches(165f)
        assertEquals(5, result165.first)
        assertEquals(5, result165.second)

        // 152.4 cm -> 5 ft 0 in (60 in)
        val result5ft = HeightConversion.cmToFeetAndInches(152.4f)
        assertEquals(5, result5ft.first)
        assertEquals(0, result5ft.second)

        // Zero or negative input
        val zeroResult = HeightConversion.cmToFeetAndInches(0f)
        assertEquals(0, zeroResult.first)
        assertEquals(0, zeroResult.second)
    }

    @Test
    fun feetAndInchesToCm_standardValues() {
        // 6 ft 0 in = 72 in * 2.54 = 182.88 -> 182.9 cm
        val cm6ft = HeightConversion.feetAndInchesToCm(6, 0)
        assertEquals(182.9f, cm6ft, 0.1f)

        // 5 ft 5 in = 65 in * 2.54 = 165.1 cm
        val cm5ft5 = HeightConversion.feetAndInchesToCm(5, 5)
        assertEquals(165.1f, cm5ft5, 0.1f)

        // 5 ft 0 in = 60 in * 2.54 = 152.4 cm
        val cm5ft = HeightConversion.feetAndInchesToCm(5, 0)
        assertEquals(152.4f, cm5ft, 0.1f)
    }

    @Test
    fun roundTripConversion() {
        // Convert ft+in to cm, then back to ft+in
        val testCases = listOf(
            Pair(4, 11),
            Pair(5, 0),
            Pair(5, 4),
            Pair(5, 8),
            Pair(6, 2),
            Pair(6, 6)
        )

        for ((feet, inches) in testCases) {
            val cm = HeightConversion.feetAndInchesToCm(feet, inches)
            val (convertedFeet, convertedInches) = HeightConversion.cmToFeetAndInches(cm)
            assertEquals("Feet mismatch for $feet ft $inches in", feet, convertedFeet)
            assertEquals("Inches mismatch for $feet ft $inches in", inches, convertedInches)
        }
    }
}

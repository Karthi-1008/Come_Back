package com.example.nutriportfolio.domain.validation

import kotlin.math.roundToInt

object HeightConversion {
    const val CM_PER_INCH = 2.54f
    const val INCHES_PER_FOOT = 12

    /**
     * Converts height in centimeters to feet and whole inches.
     */
    fun cmToFeetAndInches(cm: Float): Pair<Int, Int> {
        if (cm <= 0f) return Pair(0, 0)
        val totalInches = (cm / CM_PER_INCH).roundToInt()
        val feet = totalInches / INCHES_PER_FOOT
        val inches = totalInches % INCHES_PER_FOOT
        return Pair(feet, inches)
    }

    /**
     * Converts feet and inches to centimeters, rounded to one decimal place.
     */
    fun feetAndInchesToCm(feet: Int, inches: Int): Float {
        val totalInches = (feet * INCHES_PER_FOOT) + inches
        val cm = totalInches * CM_PER_INCH
        return (cm * 10f).roundToInt() / 10f
    }
}

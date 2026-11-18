package com.example.nutriportfolio.ui.theme

import androidx.compose.ui.graphics.Color

// Primary Emerald Palette
val Emerald50 = Color(0xFFECFDF5)
val Emerald100 = Color(0xFFD1FAE5)
val Emerald200 = Color(0xFFA7F3D0)
val Emerald300 = Color(0xFF6EE7B7)
val Emerald400 = Color(0xFF34D399)
val Emerald500 = Color(0xFF10B981)
val Emerald600 = Color(0xFF059669)
val Emerald700 = Color(0xFF047857)
val Emerald800 = Color(0xFF065F46)
val Emerald900 = Color(0xFF064E3B)

// Light Theme Neutrals (warm off-white surfaces)
val LightSurface = Color(0xFFF9FAFB)
val LightSurfaceVariant = Color(0xFFF3F4F6)
val LightBackground = Color(0xFFF8FAF9)
val LightOnSurface = Color(0xFF111827)
val LightOnSurfaceVariant = Color(0xFF4B5563)
val LightOutline = Color(0xFFE5E7EB)

// Dark Theme Neutrals (deep charcoal surfaces, not pure black)
val DarkSurface = Color(0xFF161B19)
val DarkSurfaceVariant = Color(0xFF202624)
val DarkBackground = Color(0xFF0F1312)
val DarkOnSurface = Color(0xFFF3F4F6)
val DarkOnSurfaceVariant = Color(0xFF9CA3AF)
val DarkOutline = Color(0xFF2E3834)

// Semantic Category Colors for Later Stages (Nutrient groups)
object NutrientColors {
    // Vitamins (Purple)
    val VitaminsLight = Color(0xFF8B5CF6)
    val VitaminsDark = Color(0xFFA78BFA)
    val OnVitamins = Color(0xFFFFFFFF)

    // Minerals (Blue)
    val MineralsLight = Color(0xFF3B82F6)
    val MineralsDark = Color(0xFF60A5FA)
    val OnMinerals = Color(0xFFFFFFFF)

    // Energy / Macros (Orange)
    val EnergyLight = Color(0xFFF97316)
    val EnergyDark = Color(0xFFFB923C)
    val OnEnergy = Color(0xFFFFFFFF)

    // Essential Amino Acids (Red)
    val AminoAcidsLight = Color(0xFFEF4444)
    val AminoAcidsDark = Color(0xFFF87171)
    val OnAminoAcids = Color(0xFFFFFFFF)

    // Essential Fatty Acids (Amber)
    val FattyAcidsLight = Color(0xFFF59E0B)
    val FattyAcidsDark = Color(0xFFFBBF24)
    val OnFattyAcids = Color(0xFF1F2937)

    // Bonus / Antioxidants (Teal)
    val BonusLight = Color(0xFF14B8A6)
    val BonusDark = Color(0xFF2DD4BF)
    val OnBonus = Color(0xFFFFFFFF)

    // Functional State Colors
    val SuccessLight = Color(0xFF10B981)
    val SuccessDark = Color(0xFF34D399)

    val CautionLight = Color(0xFFEAB308)
    val CautionDark = Color(0xFFFACC15)

    val ErrorLight = Color(0xFFDC2626)
    val ErrorDark = Color(0xFFEF4444)
}

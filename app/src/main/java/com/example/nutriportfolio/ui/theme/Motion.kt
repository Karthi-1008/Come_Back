package com.example.nutriportfolio.ui.theme

import android.provider.Settings
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

object MotionTokens {
    const val ScreenTransitionDuration = 260
    const val ProgressAnimationDuration = 300
    const val CardSelectionDuration = 200

    val Easing = FastOutSlowInEasing

    val ScreenEnterTransition = fadeIn(
        animationSpec = tween(durationMillis = ScreenTransitionDuration, easing = Easing)
    ) + slideInHorizontally(
        animationSpec = tween(durationMillis = ScreenTransitionDuration, easing = Easing),
        initialOffsetX = { fullWidth -> fullWidth / 8 }
    )

    val ScreenExitTransition = fadeOut(
        animationSpec = tween(durationMillis = ScreenTransitionDuration, easing = Easing)
    ) + slideOutHorizontally(
        animationSpec = tween(durationMillis = ScreenTransitionDuration, easing = Easing),
        targetOffsetX = { fullWidth -> -fullWidth / 8 }
    )

    val ScreenPopEnterTransition = fadeIn(
        animationSpec = tween(durationMillis = ScreenTransitionDuration, easing = Easing)
    ) + slideInHorizontally(
        animationSpec = tween(durationMillis = ScreenTransitionDuration, easing = Easing),
        initialOffsetX = { fullWidth -> -fullWidth / 8 }
    )

    val ScreenPopExitTransition = fadeOut(
        animationSpec = tween(durationMillis = ScreenTransitionDuration, easing = Easing)
    ) + slideOutHorizontally(
        animationSpec = tween(durationMillis = ScreenTransitionDuration, easing = Easing),
        targetOffsetX = { fullWidth -> fullWidth / 8 }
    )

    val ButtonPressSpring = spring<Float>(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessLow
    )

    fun <T> cardSelectionSpec(): AnimationSpec<T> = tween(
        durationMillis = CardSelectionDuration,
        easing = Easing
    )

    val CardSelectionColorSpec: AnimationSpec<androidx.compose.ui.graphics.Color> = tween(
        durationMillis = CardSelectionDuration,
        easing = Easing
    )
}

/**
 * Checks whether system animations are enabled or disabled ("remove animations" setting).
 */
@Composable
fun rememberAreAnimationsEnabled(): Boolean {
    val context = LocalContext.current
    return remember(context) {
        try {
            val scale = Settings.Global.getFloat(
                context.contentResolver,
                Settings.Global.ANIMATOR_DURATION_SCALE,
                1.0f
            )
            scale > 0f
        } catch (_: Exception) {
            true
        }
    }
}

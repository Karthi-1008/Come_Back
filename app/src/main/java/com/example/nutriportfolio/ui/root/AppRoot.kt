package com.example.nutriportfolio.ui.root

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.nutriportfolio.ui.main.MainScaffold
import com.example.nutriportfolio.ui.survey.SurveyScreen
import com.example.nutriportfolio.ui.survey.SurveyViewModel
import com.example.nutriportfolio.ui.theme.MotionTokens
import com.example.nutriportfolio.ui.theme.NutriPortfolioTheme
import com.example.nutriportfolio.ui.welcome.WelcomeScreen
import com.example.nutriportfolio.ui.welcome.WelcomeViewModel

@Composable
fun AppRoot(
    rootViewModel: RootViewModel,
    modifier: Modifier = Modifier
) {
    val startupState by rootViewModel.startupState.collectAsStateWithLifecycle()
    val themeMode by rootViewModel.themeMode.collectAsStateWithLifecycle()

    NutriPortfolioTheme(themeMode = themeMode) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            AnimatedContent(
                targetState = startupState,
                transitionSpec = {
                    MotionTokens.ScreenEnterTransition togetherWith MotionTokens.ScreenExitTransition
                },
                label = "appStartupTransition"
            ) { state ->
                when (state) {
                    AppStartupState.Loading -> {
                        // While startup state is being evaluated, keep surface clean
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.background)
                        )
                    }

                    AppStartupState.NeedsSignIn -> {
                        val welcomeViewModel: WelcomeViewModel = hiltViewModel()
                        WelcomeScreen(viewModel = welcomeViewModel)
                    }

                    AppStartupState.NeedsSurvey -> {
                        val surveyViewModel: SurveyViewModel = hiltViewModel()
                        SurveyScreen(
                            viewModel = surveyViewModel,
                            onFinished = {
                                // Survey saving completes and Room flow transitions state to Ready
                            }
                        )
                    }

                    is AppStartupState.Ready -> {
                        MainScaffold(profile = state.profile)
                    }
                }
            }
        }
    }
}

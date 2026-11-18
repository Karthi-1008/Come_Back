package com.example.nutriportfolio.ui.survey

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.nutriportfolio.R
import com.example.nutriportfolio.ui.survey.components.ActivityInput
import com.example.nutriportfolio.ui.survey.components.AgeInput
import com.example.nutriportfolio.ui.survey.components.DietInput
import com.example.nutriportfolio.ui.survey.components.HeightInput
import com.example.nutriportfolio.ui.survey.components.SexInput
import com.example.nutriportfolio.ui.survey.components.WeightInput
import com.example.nutriportfolio.ui.theme.MotionTokens

@Composable
fun SurveyScreen(
    viewModel: SurveyViewModel,
    onFinished: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val haptic = LocalHapticFeedback.current

    BackHandler {
        if (!viewModel.previousStep()) {
            // Step 1: Default back action will exit or follow system
        }
    }

    val animatedProgress by animateFloatAsState(
        targetValue = uiState.currentStep.toFloat() / uiState.totalSteps.toFloat(),
        animationSpec = tween(durationMillis = MotionTokens.ProgressAnimationDuration),
        label = "surveyProgress"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top section: Progress header
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(
                        id = R.string.survey_step,
                        uiState.currentStep,
                        uiState.totalSteps
                    ),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = "${(animatedProgress * 100).toInt()}%",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
        }

        // Center section: Question content inside scrollable column
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val questionTitle = when (uiState.currentStep) {
                1 -> stringResource(id = R.string.q_age)
                2 -> stringResource(id = R.string.q_sex)
                3 -> stringResource(id = R.string.q_height)
                4 -> stringResource(id = R.string.q_weight)
                5 -> stringResource(id = R.string.q_diet)
                6 -> stringResource(id = R.string.q_activity)
                else -> ""
            }

            Text(
                text = questionTitle,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(28.dp))

            AnimatedContent(
                targetState = uiState.currentStep,
                label = "surveyStepContent"
            ) { step ->
                when (step) {
                    1 -> AgeInput(
                        age = uiState.age,
                        onAgeChanged = { viewModel.setAge(it) }
                    )
                    2 -> SexInput(
                        selectedSex = uiState.sex,
                        onSexSelected = { viewModel.setSex(it) }
                    )
                    3 -> HeightInput(
                        heightCm = uiState.heightCm,
                        isFtInMode = uiState.isFtInMode,
                        onHeightChanged = { viewModel.setHeightCm(it) },
                        onUnitToggled = { viewModel.setFtInMode(it) }
                    )
                    4 -> WeightInput(
                        weightKg = uiState.weightKg,
                        onWeightChanged = { viewModel.setWeightKg(it) }
                    )
                    5 -> DietInput(
                        selectedDiet = uiState.dietType,
                        onDietSelected = { viewModel.setDietType(it) }
                    )
                    6 -> ActivityInput(
                        selectedActivity = uiState.activityLevel,
                        onActivitySelected = { viewModel.setActivityLevel(it) }
                    )
                }
            }
        }

        // Bottom section: Controls & Navigation
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (uiState.currentStep == 6) {
                Text(
                    text = stringResource(id = R.string.wellness_note),
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (uiState.currentStep > 1) {
                    OutlinedButton(
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            viewModel.previousStep()
                        },
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(54.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.btn_back),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))
                }

                Button(
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        if (uiState.currentStep == uiState.totalSteps) {
                            viewModel.finishSurvey(onFinished)
                        } else {
                            viewModel.nextStep()
                        }
                    },
                    enabled = uiState.isNextEnabled && !uiState.isSaving,
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .height(54.dp)
                ) {
                    Text(
                        text = if (uiState.currentStep == uiState.totalSteps) {
                            stringResource(id = R.string.btn_finish)
                        } else {
                            stringResource(id = R.string.btn_next)
                        },
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

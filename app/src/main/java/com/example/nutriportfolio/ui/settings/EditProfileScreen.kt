package com.example.nutriportfolio.ui.settings

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.nutriportfolio.R
import com.example.nutriportfolio.domain.model.ActivityLevel
import com.example.nutriportfolio.domain.model.DietType
import com.example.nutriportfolio.domain.model.Sex
import com.example.nutriportfolio.domain.model.UserProfile
import com.example.nutriportfolio.domain.validation.SurveyValidation
import com.example.nutriportfolio.ui.survey.components.ActivityInput
import com.example.nutriportfolio.ui.survey.components.AgeInput
import com.example.nutriportfolio.ui.survey.components.DietInput
import com.example.nutriportfolio.ui.survey.components.HeightInput
import com.example.nutriportfolio.ui.survey.components.SexInput
import com.example.nutriportfolio.ui.survey.components.WeightInput

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    currentProfile: UserProfile,
    onSaveProfile: (UserProfile) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    var age by remember { mutableIntStateOf(currentProfile.ageYears) }
    var sex by remember { mutableStateOf(currentProfile.sex) }
    var heightCm by remember { mutableFloatStateOf(currentProfile.heightCm) }
    var isFtInMode by remember { mutableStateOf(false) }
    var weightKg by remember { mutableFloatStateOf(currentProfile.weightKg) }
    var dietType by remember { mutableStateOf(currentProfile.dietType) }
    var activityLevel by remember { mutableStateOf(currentProfile.activityLevel) }

    val isFormValid = remember(age, sex, heightCm, weightKg, dietType, activityLevel) {
        SurveyValidation.isFullProfileValid(age, sex, heightCm, weightKg, dietType, activityLevel)
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.settings_edit_profile),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.btn_back)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            // Age
            Text(
                text = stringResource(id = R.string.q_age),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(16.dp))
            AgeInput(
                age = age,
                onAgeChanged = { age = it }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Sex
            Text(
                text = stringResource(id = R.string.q_sex),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(16.dp))
            SexInput(
                selectedSex = sex,
                onSexSelected = { sex = it }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Height
            Text(
                text = stringResource(id = R.string.q_height),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(16.dp))
            HeightInput(
                heightCm = heightCm,
                isFtInMode = isFtInMode,
                onHeightChanged = { heightCm = it },
                onUnitToggled = { isFtInMode = it }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Weight
            Text(
                text = stringResource(id = R.string.q_weight),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(16.dp))
            WeightInput(
                weightKg = weightKg,
                onWeightChanged = { weightKg = it }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Diet
            Text(
                text = stringResource(id = R.string.q_diet),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(16.dp))
            DietInput(
                selectedDiet = dietType,
                onDietSelected = { dietType = it }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Activity Level
            Text(
                text = stringResource(id = R.string.q_activity),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(16.dp))
            ActivityInput(
                selectedActivity = activityLevel,
                onActivitySelected = { activityLevel = it }
            )

            Spacer(modifier = Modifier.height(36.dp))

            // Save button
            Button(
                onClick = {
                    if (isFormValid) {
                        val updated = currentProfile.copy(
                            ageYears = age,
                            sex = sex,
                            heightCm = heightCm,
                            weightKg = weightKg,
                            dietType = dietType,
                            activityLevel = activityLevel,
                            updatedAt = System.currentTimeMillis()
                        )
                        onSaveProfile(updated)
                        Toast.makeText(
                            context,
                            context.getString(R.string.profile_saved_toast),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                enabled = isFormValid,
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.btn_save),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

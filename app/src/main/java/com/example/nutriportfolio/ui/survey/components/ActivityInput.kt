package com.example.nutriportfolio.ui.survey.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.nutriportfolio.R
import com.example.nutriportfolio.domain.model.ActivityLevel

@Composable
fun ActivityInput(
    selectedActivity: ActivityLevel?,
    onActivitySelected: (ActivityLevel) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        SelectableChoiceCard(
            title = stringResource(id = R.string.activity_sedentary),
            isSelected = selectedActivity == ActivityLevel.SEDENTARY,
            onClick = { onActivitySelected(ActivityLevel.SEDENTARY) }
        )

        Spacer(modifier = Modifier.height(14.dp))

        SelectableChoiceCard(
            title = stringResource(id = R.string.activity_light),
            isSelected = selectedActivity == ActivityLevel.LIGHT,
            onClick = { onActivitySelected(ActivityLevel.LIGHT) }
        )

        Spacer(modifier = Modifier.height(14.dp))

        SelectableChoiceCard(
            title = stringResource(id = R.string.activity_moderate),
            isSelected = selectedActivity == ActivityLevel.MODERATE,
            onClick = { onActivitySelected(ActivityLevel.MODERATE) }
        )

        Spacer(modifier = Modifier.height(14.dp))

        SelectableChoiceCard(
            title = stringResource(id = R.string.activity_very),
            isSelected = selectedActivity == ActivityLevel.VERY_ACTIVE,
            onClick = { onActivitySelected(ActivityLevel.VERY_ACTIVE) }
        )
    }
}

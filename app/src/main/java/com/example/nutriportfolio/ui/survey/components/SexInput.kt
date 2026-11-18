package com.example.nutriportfolio.ui.survey.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.nutriportfolio.R
import com.example.nutriportfolio.domain.model.Sex

@Composable
fun SexInput(
    selectedSex: Sex?,
    onSexSelected: (Sex) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = stringResource(id = R.string.q_sex_hint),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        SelectableChoiceCard(
            title = stringResource(id = R.string.sex_male),
            isSelected = selectedSex == Sex.MALE,
            onClick = { onSexSelected(Sex.MALE) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        SelectableChoiceCard(
            title = stringResource(id = R.string.sex_female),
            isSelected = selectedSex == Sex.FEMALE,
            onClick = { onSexSelected(Sex.FEMALE) }
        )
    }
}

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
import com.example.nutriportfolio.domain.model.DietType

@Composable
fun DietInput(
    selectedDiet: DietType?,
    onDietSelected: (DietType) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        SelectableChoiceCard(
            title = stringResource(id = R.string.diet_veg),
            isSelected = selectedDiet == DietType.VEG,
            onClick = { onDietSelected(DietType.VEG) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        SelectableChoiceCard(
            title = stringResource(id = R.string.diet_nonveg),
            isSelected = selectedDiet == DietType.NON_VEG,
            onClick = { onDietSelected(DietType.NON_VEG) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        SelectableChoiceCard(
            title = stringResource(id = R.string.diet_vegan),
            isSelected = selectedDiet == DietType.VEGAN,
            onClick = { onDietSelected(DietType.VEGAN) }
        )
    }
}

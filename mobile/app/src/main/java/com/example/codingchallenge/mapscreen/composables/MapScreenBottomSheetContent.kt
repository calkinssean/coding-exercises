package com.example.codingchallenge.mapscreen.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.codingchallenge.common.composables.CSFlowRow
import com.example.codingchallenge.common.composables.CSTextField
import com.example.codingchallenge.mapscreen.MapScreenInteractions
import com.example.codingchallenge.mapscreen.model.MapScreenModel
import com.example.codingchallenge.ui.theme.CodingChallengeTheme

@Composable
fun MapScreenBottomSheetContent(
    modifier: Modifier = Modifier,
    model: MapScreenModel,
    interactions: MapScreenInteractions
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CSTextField(
            modifier = Modifier.fillMaxWidth(),
            value = model.searchQuery,
            onValueChange = interactions.onSearchQueryChanged,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = Color.LightGray
                )
            },
            placeholder = {
                Text(text = "Search Maps", color = Color.LightGray)
            }
        )
        CSFlowRow(modifier = Modifier.fillMaxWidth(), items = model.locationTypes, selectedItems = model.selectedLocationTypes, onItemClicked = interactions.onLocationTypeSelected)
    }
}

@Preview
@Composable
private fun MapScreenBottomSheetContentPreview() {
    CodingChallengeTheme {
        MapScreenBottomSheetContent(
            modifier = Modifier.fillMaxWidth(),
            model = MapScreenModel(),
            interactions = MapScreenInteractions.EMPTY
        )
    }

}
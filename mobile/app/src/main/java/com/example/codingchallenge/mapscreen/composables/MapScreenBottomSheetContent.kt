package com.example.codingchallenge.mapscreen.composables

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import com.example.codingchallenge.common.composables.CSFlowRow
import com.example.codingchallenge.common.composables.CSTextField
import com.example.codingchallenge.common.composables.CircularIcon
import com.example.codingchallenge.mapscreen.MapScreenInteractions
import com.example.codingchallenge.mapscreen.model.Location
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
        verticalArrangement = Arrangement.spacedBy(24.dp)
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
        CSFlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            items = model.locationTypes,
            selectedItems = model.selectedLocationTypes,
            onItemClicked = interactions.onLocationTypeSelected
        )
        if (model.searchQuery.isNotBlank()) {
            model.searchResults.forEach {
                SearchResultRow(modifier = Modifier.fillMaxWidth(), location = it)
            }
        }
    }
}

@Composable
private fun SearchResultRow(modifier: Modifier = Modifier, location: Location) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        CircularIcon(modifier = Modifier.padding(8.dp).size(48.dp), color = location.color, resourceId = location.iconId)
        Column(modifier = Modifier.weight(1f)) {
            Text(text = location.name, style = MaterialTheme.typography.titleMedium)
            Text(text = location.description, style = MaterialTheme.typography.bodyMedium)
        }
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
package com.example.codingchallenge.mapscreen.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.codingchallenge.R
import com.example.codingchallenge.common.composables.CSFlowRow
import com.example.codingchallenge.common.composables.CSTextField
import com.example.codingchallenge.common.composables.CircularIcon
import com.example.codingchallenge.mapscreen.MapScreenInteractions
import com.example.codingchallenge.mapscreen.model.Attribute
import com.example.codingchallenge.mapscreen.model.Location
import com.example.codingchallenge.mapscreen.model.MapScreenModel
import com.example.codingchallenge.ui.theme.CodingChallengeTheme
import com.example.codingchallenge.ui.theme.TextFieldTextColor

@Composable
fun MapScreenBottomSheetContent(
    modifier: Modifier = Modifier,
    model: MapScreenModel,
    onSearchFieldFocused: () -> Unit = {},
    onSearchResultSelected: (Location) -> Unit,
    interactions: MapScreenInteractions,
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .padding(bottom = 24.dp)
    ) {
        CSTextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { if (it.isFocused) onSearchFieldFocused() },
            value = model.searchQuery,
            onValueChange = interactions.onSearchQueryChanged,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = TextFieldTextColor
                )
            },
            trailingIcon = if (model.searchQuery.isEmpty()) {
                null
            } else {
                {
                    Icon(
                        modifier = Modifier.clickable { interactions.onSearchQueryChanged("") },
                        painter = painterResource(id = R.drawable.ic_close),
                        tint = TextFieldTextColor,
                        contentDescription = "Clear Search Text"
                    )
                }
            },
            placeholder = {
                Text(text = stringResource(id = R.string.search_maps), color = TextFieldTextColor)
            }
        )
        CSFlowRow(
            modifier = Modifier
                .padding(top = 36.dp, bottom = 24.dp)
                .fillMaxWidth(),
            items = model.locationTypes,
            selectedItems = model.selectedLocationTypes,
            onItemClicked = interactions.onLocationTypeSelected,
            onClearAllClicked = interactions.onClearAllLocationTypesClicked
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(model.searchResults) {
                SearchResultRow(modifier = Modifier.fillMaxWidth(), location = it, onClick = { onSearchResultSelected(it) })
            }
        }
    }
}

@Composable
private fun SearchResultRow(modifier: Modifier = Modifier, location: Location, onClick: () -> Unit) {
    Row(modifier = modifier.clickable { onClick() }, verticalAlignment = Alignment.CenterVertically) {
        CircularIcon(
            modifier = Modifier
                .padding(8.dp)
                .size(48.dp),
            color = location.color,
            resourceId = location.iconId
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(text = location.name, style = MaterialTheme.typography.titleMedium)
            Text(text = location.description, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview
@Composable
private fun MapScreenBottomSheetContentPreview() {
    val locations = listOf(
        Location(
            id = 0,
            latitude = 0.0,
            longitude = 0.0,
            attributes = listOf(Attribute(type = "location_type", value = "Value1"))
        ),
        Location(
            id = 0,
            latitude = 0.0,
            longitude = 0.0,
            attributes = listOf(Attribute(type = "location_type", value = "Value2"))
        ),
        Location(
            id = 0,
            latitude = 0.0,
            longitude = 0.0,
            attributes = listOf(Attribute(type = "location_type", value = "Value3"))
        ),
        Location(
            id = 0,
            latitude = 0.0,
            longitude = 0.0,
            attributes = listOf(Attribute(type = "location_type", value = "Value4"))
        )
    )
    val selectedLocationTypes = listOf(locations.shuffled().first().attributes.first())
    val model = MapScreenModel(locations = locations, selectedLocationTypes = selectedLocationTypes)
    CodingChallengeTheme {
        MapScreenBottomSheetContent(
            modifier = Modifier.fillMaxWidth(),
            model = model,
            onSearchResultSelected = {},
            interactions = MapScreenInteractions.EMPTY
        )
    }
}
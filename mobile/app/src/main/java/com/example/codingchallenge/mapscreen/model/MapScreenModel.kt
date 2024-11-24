package com.example.codingchallenge.mapscreen.model

import com.example.codingchallenge.common.CommonErrors
import com.example.codingchallenge.common.LoadState
import org.osmdroid.views.overlay.Marker

data class MapScreenModel(
    val loadState: LoadState = LoadState.Loading,
    val locations: List<Location> = emptyList(),
    val selectedLocationTypes: List<Attribute> = emptyList(),
    val error: CommonErrors? = null,
    val searchQuery: String = "",
    val selectedLocation: Location? = null,
    val shouldPanToSelectedLocation: Boolean = false
) {
    val locationTypes: List<Attribute> =
        locations.flatMap { it.attributes }.filter { it.type == "location_type" }.distinct()

    fun filteredByType(): List<Location> {
        return filterByTypes(locations)
    }

    private fun filterByTypes(locations: List<Location>): List<Location> {
        if (selectedLocationTypes.isEmpty()) return locations
        return locations
            .filter { location ->
            location.attributes.any { attribute ->
                selectedLocationTypes.contains(attribute)
            }
        }
    }

    val searchResults: List<Location> =
        filteredByType().filter {
            it.name.contains(
                searchQuery,
                ignoreCase = true
            ) || it.locationType.contains(searchQuery, ignoreCase = true)
        }
}
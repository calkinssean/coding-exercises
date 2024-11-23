package com.example.codingchallenge.mapscreen.model

import com.example.codingchallenge.common.CommonErrors
import com.example.codingchallenge.common.LoadState

data class MapScreenModel(
    val loadState: LoadState = LoadState.Loading,
    val locations: List<Location> = emptyList(),
    val selectedLocationTypes: List<Attribute> = emptyList(),
    val error: CommonErrors? = null,
    val searchQuery: String = ""
) {
    val locationTypes: List<Attribute> =
        locations.flatMap { it.attributes }.filter { it.type == "location_type" }.distinct()

    fun filteredLocations(): List<Location> {
        return filterByTypes(locations)
    }

    private fun filterByTypes(locations: List<Location>): List<Location> {
        if (selectedLocationTypes.isEmpty()) return locations
        return locations.filter { location ->
            location.attributes.any { attribute ->
                selectedLocationTypes.contains(attribute)
            }
        }
    }

//    private fun filterBySearchQuery(locations: List<Location>): List<Location> {
//        if (searchQuery.isEmpty()) return locations
//        return locations.filter { location ->
//        }
//    }



}
package com.example.codingchallenge.mapscreen

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codingchallenge.common.GenericError
import com.example.codingchallenge.common.LoadState
import com.example.codingchallenge.mapscreen.model.Attribute
import com.example.codingchallenge.mapscreen.model.Location
import com.example.codingchallenge.mapscreen.model.MapScreenModel
import com.example.codingchallenge.repositories.LocationsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.osmdroid.views.overlay.Marker
import javax.inject.Inject

@HiltViewModel
class MapScreenViewModel @Inject constructor(private val locationsRepository: LocationsRepository) :
    ViewModel() {

    @VisibleForTesting
    val mutableModel = MutableStateFlow(MapScreenModel(loadState = LoadState.Loading))
    val observableModel: StateFlow<MapScreenModel> = mutableModel
    @VisibleForTesting
    val latestModel: MapScreenModel
        get() = mutableModel.value

    fun onStart() {
        fetchLocations()
    }

    @VisibleForTesting
    fun fetchLocations() {
        viewModelScope.launch {
            try {
                val locations = locationsRepository.getLocations()
                mutableModel.update { it.copy(loadState = LoadState.None, locations = locations) }
            } catch (e: Exception) {
                handleException(e)
            }
        }
    }

    private fun handleException(e: Exception) {
        mutableModel.update { it.copy(loadState = LoadState.None, error = GenericError) }
    }

    fun onRetry() {
        mutableModel.update { it.copy(loadState = LoadState.Loading, error = null) }
        fetchLocations()
    }

    fun onSearchQueryChanged(searchQuery: String) {
        mutableModel.update { it.copy(searchQuery = searchQuery) }
    }

    fun onLocationTypeSelected(locationType: Attribute) {
        val selectedLocationTypes = latestModel.selectedLocationTypes.toMutableList()
        if (selectedLocationTypes.contains(locationType)) {
            selectedLocationTypes.remove(locationType)
        } else {
            selectedLocationTypes.add(locationType)
        }
        mutableModel.update { it.copy(selectedLocationTypes = selectedLocationTypes) }
    }

    fun onClearAllLocationTypesClicked() {
        mutableModel.update { it.copy(selectedLocationTypes = emptyList()) }
    }

    fun onLocationSelected(location: Location?) {
        mutableModel.update { it.copy(selectedLocation = location) }
    }

    fun onSearchResultSelected(location: Location) {
        mutableModel.update {
            it.copy(
                selectedLocation = location,
                shouldPanToSelectedLocation = true
            )
        }
    }

    fun didPanToSelectedLocation() {
        mutableModel.update { it.copy(shouldPanToSelectedLocation = false) }
    }

    fun onUpdateShouldRecenterMap(shouldRecenterMap: Boolean) {
        mutableModel.update { it.copy(shouldRecenterMap = shouldRecenterMap) }
    }
}
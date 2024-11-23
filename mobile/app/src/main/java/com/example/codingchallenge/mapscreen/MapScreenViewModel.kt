package com.example.codingchallenge.mapscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codingchallenge.common.GenericError
import com.example.codingchallenge.common.LoadState
import com.example.codingchallenge.mapscreen.model.Attribute
import com.example.codingchallenge.mapscreen.model.MapScreenModel
import com.example.codingchallenge.repositories.LocationsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapScreenViewModel @Inject constructor(private val locationsRepository: LocationsRepository) :
    ViewModel() {

    private val mutableModel = MutableStateFlow(MapScreenModel(loadState = LoadState.Loading))
    val observableModel: StateFlow<MapScreenModel> = mutableModel
    private val latestModel: MapScreenModel
        get() = mutableModel.value

    fun onStart() {
        fetchLocations()
    }

    private fun fetchLocations() {
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
        Log.d("MapScreenViewModel", "Exception: $e")
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
}
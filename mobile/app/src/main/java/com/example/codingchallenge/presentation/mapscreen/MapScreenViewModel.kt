package com.example.codingchallenge.presentation.mapscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codingchallenge.common.LoadState
import com.example.codingchallenge.domain.repository.LocationsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapScreenViewModel @Inject constructor(private val locationsRepository: LocationsRepository) : ViewModel() {

    private val mutableModel = MutableStateFlow(MapScreenModel(loadState = LoadState.Loading))

    val observableModel: StateFlow<MapScreenModel> = mutableModel

    private var isInitialized = false

    fun onStart() {
        Log.d("MapScreenViewModel", "onStart: onStart")
        if (isInitialized) return

        isInitialized = true

        fetchLocations()
    }

    private fun fetchLocations() {
        viewModelScope.launch {
            try {
                val locations = locationsRepository.getLocations()
                Log.d("MapScreenViewModel", "Got Locations: $locations")
                mutableModel.update { it.copy(loadState = LoadState.None, locations = locations) }
            } catch (e: Exception) {
                handleException(e)
            }
        }
    }

    private fun handleException(e: Exception) {
        Log.d("MapScreenViewModel", "Exception fetching locations: $e")
        // TODO
    }
}
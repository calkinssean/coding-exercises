package com.example.codingchallenge.presentation.mapscreen

import com.example.codingchallenge.common.LoadState
import com.example.codingchallenge.domain.model.Location

data class MapScreenModel(
    val loadState: LoadState = LoadState.Loading,
    val locations: List<Location> = emptyList()
)
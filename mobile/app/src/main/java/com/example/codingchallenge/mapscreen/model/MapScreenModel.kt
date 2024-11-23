package com.example.codingchallenge.mapscreen.model

import com.example.codingchallenge.common.CommonScreenErrors
import com.example.codingchallenge.common.LoadState

data class MapScreenModel(
    val loadState: LoadState = LoadState.Loading,
    val locations: List<Location> = emptyList(),
    val error: CommonScreenErrors? = null
)
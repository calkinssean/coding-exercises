package com.example.codingchallenge.presentation.mapscreen

import androidx.activity.result.launch
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.codingchallenge.domain.model.Location
import com.example.codingchallenge.presentation.mapscreen.composables.OSMMapView
import com.example.codingchallenge.ui.theme.CodingChallengeTheme

const val MapScreenRoute = "MapScreenRoute"

fun NavGraphBuilder.mapScreen() {
    composable(
        route = MapScreenRoute
    ) { backStackEntry ->
        val viewModel: MapScreenViewModel = hiltViewModel()
        val model by viewModel.observableModel.collectAsStateWithLifecycle()
        LifecycleStartEffect(
            key1 = viewModel,
            lifecycleOwner = LocalLifecycleOwner.current,
            effects = {
                viewModel.onStart()
                onStopOrDispose {}
            }
        )

        MapScreen(modifier = Modifier.fillMaxSize(), model = model)

    }
}

@Composable
private fun MapScreen(modifier: Modifier = Modifier, model: MapScreenModel) {
    MapScreenContent(modifier = modifier, model = model)
}

@Composable
private fun MapScreenContent(modifier: Modifier = Modifier, model: MapScreenModel) {
    OSMMapView(modifier = modifier, locations = model.locations)
}

@Preview
@Composable
private fun MapScreenContentPreview() {
    CodingChallengeTheme {
        val model = MapScreenModel()
        MapScreenContent(modifier = Modifier.fillMaxSize(), model = model)
    }
}
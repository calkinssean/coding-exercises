package com.example.codingchallenge.mapscreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
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
import com.example.codingchallenge.common.LoadState
import com.example.codingchallenge.common.composables.ErrorScreen
import com.example.codingchallenge.common.composables.LoadingScreen
import com.example.codingchallenge.mapscreen.composables.OSMMapView
import com.example.codingchallenge.mapscreen.model.MapScreenModel
import com.example.codingchallenge.ui.theme.CodingChallengeTheme

const val MapScreenRoute = "MapScreenRoute"
private data class MapScreenInteractions(
    val onRetry: () -> Unit
)

fun NavGraphBuilder.mapScreen() {
    composable(
        route = MapScreenRoute
    ) { backStackEntry ->
        val viewModel: MapScreenViewModel = hiltViewModel()
        val model by viewModel.observableModel.collectAsStateWithLifecycle()

        val interactions = MapScreenInteractions(
            onRetry = viewModel::onRetry
        )

        LifecycleStartEffect(
            key1 = viewModel,
            lifecycleOwner = LocalLifecycleOwner.current,
            effects = {
                viewModel.onStart()
                onStopOrDispose {}
            }
        )

        MapScreen(modifier = Modifier.fillMaxSize(), model = model, interactions = interactions)

    }
}

@Composable
private fun MapScreen(modifier: Modifier = Modifier, model: MapScreenModel, interactions: MapScreenInteractions) {
    Scaffold(modifier = modifier) { paddingValues ->
        when {
            model.loadState == LoadState.Loading -> LoadingScreen(modifier = Modifier.fillMaxSize())
            model.error != null -> ErrorScreen(modifier = Modifier.fillMaxSize(), onRetry = interactions.onRetry)
            else -> MapScreenContent(modifier = modifier.padding(paddingValues), model = model)
        }
    }
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
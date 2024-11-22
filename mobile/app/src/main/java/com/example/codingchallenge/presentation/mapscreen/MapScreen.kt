package com.example.codingchallenge.presentation.mapscreen

import androidx.activity.result.launch
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.codingchallenge.presentation.mapscreen.composables.OSMMapView
import com.example.codingchallenge.ui.theme.CodingChallengeTheme

const val MapScreenRoute = "MapScreenRoute"

fun NavGraphBuilder.mapScreen() {
    composable(
        route = MapScreenRoute
    ) { backStackEntry ->
        val viewModel: MapScreenViewModel = hiltViewModel()

        LifecycleStartEffect(
            key1 = viewModel,
            lifecycleOwner = LocalLifecycleOwner.current,
            effects = {
                viewModel.onStart()
                onStopOrDispose {}
            }
        )

        MapScreen(modifier = Modifier.fillMaxSize())

    }
}

@Composable
private fun MapScreen(modifier: Modifier = Modifier) {
    MapScreenContent(modifier = modifier)
}

@Composable
private fun MapScreenContent(modifier: Modifier = Modifier) {
    OSMMapView(modifier = modifier)
}

@Preview
@Composable
private fun MapScreenContentPreview() {
    CodingChallengeTheme {
        MapScreenContent(modifier = Modifier.fillMaxSize())
    }
}
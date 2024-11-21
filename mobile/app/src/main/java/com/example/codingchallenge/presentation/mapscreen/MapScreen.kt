package com.example.codingchallenge.presentation.mapscreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

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

        MapScreen()

    }
}

@Composable
fun MapScreen() {
    Text(text = "MapScreen")
}
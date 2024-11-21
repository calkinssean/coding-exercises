package com.example.codingchallenge.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.codingchallenge.presentation.mapscreen.MapScreenRoute
import com.example.codingchallenge.presentation.mapscreen.mapScreen

@Composable
fun NavigationHost(navController: NavHostController) {

    NavHost(
        modifier = Modifier,
        navController = navController,
        startDestination = MapScreenRoute
    ) {
        mapScreen()
    }

}


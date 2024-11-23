package com.example.codingchallenge

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.codingchallenge.mapscreen.MapScreenRoute
import com.example.codingchallenge.mapscreen.mapScreen

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


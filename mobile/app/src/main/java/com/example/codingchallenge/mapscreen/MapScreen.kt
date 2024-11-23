package com.example.codingchallenge.mapscreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.codingchallenge.common.LoadState
import com.example.codingchallenge.common.composables.ErrorScreen
import com.example.codingchallenge.common.composables.LoadingScreen
import com.example.codingchallenge.mapscreen.composables.MapScreenBottomSheetContent
import com.example.codingchallenge.mapscreen.composables.OSMMapView
import com.example.codingchallenge.mapscreen.model.Attribute
import com.example.codingchallenge.mapscreen.model.MapScreenModel
import com.example.codingchallenge.ui.theme.CodingChallengeTheme

const val MapScreenRoute = "MapScreenRoute"

data class MapScreenInteractions(
    val onRetry: () -> Unit,
    val onSearchQueryChanged: (String) -> Unit,
    val onLocationTypeSelected: (Attribute) -> Unit,
    val onClearAllLocationTypesClicked: () -> Unit
) {

    companion object {
        val EMPTY = MapScreenInteractions(
            onRetry = {},
            onSearchQueryChanged = {},
            onLocationTypeSelected = {},
            onClearAllLocationTypesClicked = {}
        )
    }
}

fun NavGraphBuilder.mapScreen() {
    composable(
        route = MapScreenRoute
    ) { backStackEntry ->
        val viewModel: MapScreenViewModel = hiltViewModel()
        val model by viewModel.observableModel.collectAsStateWithLifecycle()

        val interactions = MapScreenInteractions(
            onRetry = viewModel::onRetry,
            onSearchQueryChanged = viewModel::onSearchQueryChanged,
            onLocationTypeSelected = viewModel::onLocationTypeSelected,
            onClearAllLocationTypesClicked = viewModel::onClearAllLocationTypesClicked
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
private fun MapScreen(
    modifier: Modifier = Modifier,
    model: MapScreenModel,
    interactions: MapScreenInteractions
) {
    when {
        model.loadState == LoadState.Loading -> LoadingScreen(modifier = modifier)
        model.error != null -> ErrorScreen(modifier = modifier, onRetry = interactions.onRetry)
        else -> MapScreenContent(modifier = modifier, model = model, interactions = interactions)
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MapScreenContent(
    modifier: Modifier = Modifier,
    model: MapScreenModel,
    interactions: MapScreenInteractions
) {
    val context = LocalContext.current
    val sheetState by remember { mutableStateOf(SheetState(
        skipPartiallyExpanded = false,
        initialValue = SheetValue.PartiallyExpanded,
        density = Density(context),
        confirmValueChange = {
            it != SheetValue.Hidden
        }
    )) }
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    BottomSheetScaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        sheetContent = {
            MapScreenBottomSheetContent(
                modifier = Modifier.fillMaxWidth(),
                model = model,
                interactions = interactions
            )
        },
        sheetShape = RoundedCornerShape(16.dp),
        sheetPeekHeight = 150.dp
    ) { paddingValues ->
        OSMMapView(modifier = modifier.padding(paddingValues), locations = model.filteredLocations())
    }
}

@Preview
@Composable
private fun MapScreenContentPreview() {
    CodingChallengeTheme {
        val model = MapScreenModel()
        MapScreenContent(
            modifier = Modifier.fillMaxSize(),
            model = model,
            interactions = MapScreenInteractions.EMPTY
        )
    }
}
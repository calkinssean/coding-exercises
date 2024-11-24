package com.example.codingchallenge.mapscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.codingchallenge.R
import com.example.codingchallenge.common.LoadState
import com.example.codingchallenge.common.composables.ErrorScreen
import com.example.codingchallenge.common.composables.LoadingScreen
import com.example.codingchallenge.mapscreen.composables.LocationDetailBottomSheetContent
import com.example.codingchallenge.mapscreen.composables.MapScreenBottomSheetContent
import com.example.codingchallenge.mapscreen.composables.OSMMapView
import com.example.codingchallenge.mapscreen.model.Attribute
import com.example.codingchallenge.mapscreen.model.Location
import com.example.codingchallenge.mapscreen.model.MapScreenModel
import com.example.codingchallenge.ui.theme.CodingChallengeTheme
import com.example.codingchallenge.ui.theme.TextFieldTextColor
import kotlinx.coroutines.launch

const val MapScreenRoute = "MapScreenRoute"
private val peekHeight = 140.dp
private val partiallyExpandedHeight = 236.dp

data class MapScreenInteractions(
    val onRetry: () -> Unit,
    val onSearchQueryChanged: (String) -> Unit,
    val onLocationTypeSelected: (Attribute) -> Unit,
    val onClearAllLocationTypesClicked: () -> Unit,
    val onLocationSelected: (Location?) -> Unit,
    val onSearchResultSelected: (Location) -> Unit,
    val didPanToSelectedLocation: () -> Unit,
    val onUpdateShouldRecenterMap: (Boolean) -> Unit
) {

    companion object {
        val EMPTY = MapScreenInteractions(
            onRetry = {},
            onSearchQueryChanged = {},
            onLocationTypeSelected = {},
            onClearAllLocationTypesClicked = {},
            onLocationSelected = {},
            onSearchResultSelected = {},
            didPanToSelectedLocation = {},
            onUpdateShouldRecenterMap = {}
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
            onClearAllLocationTypesClicked = viewModel::onClearAllLocationTypesClicked,
            onLocationSelected = viewModel::onLocationSelected,
            onSearchResultSelected = viewModel::onSearchResultSelected,
            didPanToSelectedLocation = viewModel::didPanToSelectedLocation,
            onUpdateShouldRecenterMap = viewModel::onUpdateShouldRecenterMap
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
    var sheetHeight by remember { mutableStateOf(peekHeight) }
    var skipPartiallyExpanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val detailBottomSheetState by remember {
        mutableStateOf(
            SheetState(
                skipPartiallyExpanded = false,
                initialValue = SheetValue.Hidden,
                density = Density(context),
                confirmValueChange = {
                    if (it == SheetValue.Hidden) {
                        interactions.onLocationSelected(null)
                    }
                    true
                }
            )
        )
    }
    val sheetState by remember {
        mutableStateOf(SheetState(
            skipPartiallyExpanded = false,
            initialValue = SheetValue.Hidden,
            density = Density(context),
            confirmValueChange = {
                if (it != SheetValue.Expanded) {
                    focusManager.clearFocus()
                }
                if (it == SheetValue.PartiallyExpanded) {
                    if (skipPartiallyExpanded) {
                        skipPartiallyExpanded = false
                    } else {
                        sheetHeight = partiallyExpandedHeight
                    }
                }
                if (it == SheetValue.Hidden) {
                    skipPartiallyExpanded = true
                    sheetHeight = peekHeight
                }
                it != SheetValue.Hidden
            }
        ))
    }
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )

    BottomSheetScaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        sheetContainerColor = Color.White,
        sheetSwipeEnabled = model.selectedLocation == null,
        sheetContent = {
            MapScreenBottomSheetContent(
                modifier = Modifier.fillMaxSize(),
                model = model,
                interactions = interactions,
                onSearchFieldFocused = {
                    scope.launch {
                        sheetState.expand()
                    }
                },
                onSearchResultSelected = {
                    scope.launch {
                        sheetState.partialExpand()
                    }
                    focusManager.clearFocus()
                    interactions.onSearchResultSelected(it)
                }
            )
        },
        sheetShape = RoundedCornerShape(16.dp),
        sheetPeekHeight = sheetHeight
    ) { _ ->
        Box(modifier = Modifier.fillMaxSize()) {
            OSMMapView(
                modifier = Modifier.fillMaxSize(),
                model = model,
                interactions = interactions
            )
            Box(
                modifier = Modifier
                    .padding(end = 12.dp, top = 32.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .clickable { interactions.onUpdateShouldRecenterMap(true) }
                    .align(Alignment.TopEnd)
            ) {
                Icon(
                    modifier = Modifier
                        .padding(4.dp),
                    painter = painterResource(id = R.drawable.ic_location),
                    tint = TextFieldTextColor,
                    contentDescription = null
                )
            }

        }

    }
    model.selectedLocation?.let {
        ModalBottomSheet(
            modifier = Modifier.fillMaxWidth(),
            containerColor = Color.White,
            onDismissRequest = {
                scope.launch {
                    detailBottomSheetState.hide()
                    interactions.onLocationSelected(null)
                }
            },
            sheetState = detailBottomSheetState,
            content = {
                LocationDetailBottomSheetContent(
                    modifier = Modifier.fillMaxWidth(),
                    location = it
                )
            }
        )
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
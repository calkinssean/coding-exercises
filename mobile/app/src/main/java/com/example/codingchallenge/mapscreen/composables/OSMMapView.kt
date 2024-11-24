package com.example.codingchallenge.mapscreen.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.codingchallenge.common.composables.CircularIcon
import com.example.codingchallenge.mapscreen.MapScreenInteractions
import com.example.codingchallenge.mapscreen.model.Location
import com.example.codingchallenge.mapscreen.model.MapScreenModel
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController.Visibility
import org.osmdroid.views.MapView

private val SAN_FRANCISCO = GeoPoint(37.7749, -122.4194)
private const val DEFAULT_ZOOM_LEVEL = 17.0
private const val SELECTED_LOCATION_ZOOM_LEVEL = 18.5
private const val PAN_SPEED = 800L

@Composable
fun OSMMapView(modifier: Modifier = Modifier, model: MapScreenModel, interactions: MapScreenInteractions) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            MapView(context)
                .apply {
                    zoomController.setVisibility(Visibility.NEVER)
                    setMultiTouchControls(true)
                    setTileSource(TileSourceFactory.MAPNIK)
                    controller.setZoom(DEFAULT_ZOOM_LEVEL)
                    controller.setCenter(SAN_FRANCISCO)
                }
        },
        update = { mapView ->
            // Filter out selected location
            val locations = model.filteredByType().filter { it != model.selectedLocation }

            if (model.shouldRecenterMap) {
                interactions.onUpdateShouldRecenterMap(false)
                mapView.controller.animateTo(SAN_FRANCISCO, DEFAULT_ZOOM_LEVEL, PAN_SPEED)
            }

            mapView.overlays.clear()
            val context = mapView.context
            locations.forEach { location ->
                val marker = location.toMarker(context, mapView)
                marker.setOnMarkerClickListener { marker, mapView ->
                    interactions.onLocationSelected(location)
                    false
                }
                mapView.overlays.add(marker)
            }

            model.selectedLocation?.let {
                val marker = it.toMarker(context, mapView, true)
                mapView.overlays.add(marker)
                if (model.shouldPanToSelectedLocation) {
                    // Reset shouldPanToSelectedLocation property after animation
                    interactions.didPanToSelectedLocation()
                    mapView.controller.animateTo(marker.position, SELECTED_LOCATION_ZOOM_LEVEL, PAN_SPEED)
                }
            }
            mapView.invalidate()
        }
    )
}
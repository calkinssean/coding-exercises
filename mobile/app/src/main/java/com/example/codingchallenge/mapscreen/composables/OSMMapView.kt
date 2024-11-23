package com.example.codingchallenge.mapscreen.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.codingchallenge.mapscreen.model.Location
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController.Visibility
import org.osmdroid.views.MapView

private val SAN_FRANCISCO = GeoPoint(37.7749, -122.4194)
private const val ZOOM_LEVEL = 17.0

@Composable
fun OSMMapView(modifier: Modifier = Modifier, locations: List<Location>) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            MapView(context)
                .apply {
                    zoomController.setVisibility(Visibility.NEVER)
                    setMultiTouchControls(true)
                    setTileSource(TileSourceFactory.MAPNIK)
                    controller.setZoom(ZOOM_LEVEL)
                    controller.setCenter(SAN_FRANCISCO)
                }
        },
        update = { mapView ->
            val context = mapView.context
            locations.forEach { location ->
                val marker = location.toMarker(context, mapView)
                mapView.overlays.add(marker)
            }
            mapView.invalidate()
        }
    )
}
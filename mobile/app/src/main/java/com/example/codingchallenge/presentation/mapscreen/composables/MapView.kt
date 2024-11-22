package com.example.codingchallenge.presentation.mapscreen.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView

private val SAN_FRANCISCO = GeoPoint(37.7749, -122.4194)

@Composable
fun OSMMapView(modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            MapView(context)
                .apply {
                    setTileSource(TileSourceFactory.MAPNIK)
                    controller.setZoom(120.0)
                    controller.setCenter(SAN_FRANCISCO)
                }

        },
        update = { view ->
        }
    )
}
package com.example.codingchallenge.mapscreen.model

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import com.example.codingchallenge.R
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

data class Location(
    val attributes: List<Attribute>,
    val id: Int,
    val latitude: Double,
    val longitude: Double
) {
    private val position: GeoPoint
        get() = GeoPoint(latitude, longitude)

    private val locationType: String
        get() = attributes.firstOrNull { it.type == "location_type" }?.value ?: ""

    fun toMarker(context: Context, mapView: MapView): Marker {
        val marker = Marker(mapView)
        marker.position = position
        marker.icon = icon(context)
        return marker
    }

    private fun icon(context: Context): Drawable? {
        return when (locationType) {
            "restaurant" -> context.getDrawable(R.drawable.restaurant_icon)
            "bar" -> context.getDrawable(R.drawable.bar_icon)
            "cafe" -> context.getDrawable(R.drawable.cafe_icon)
            "museum" -> context.getDrawable(R.drawable.museum_icon)
            "landmark" -> context.getDrawable(R.drawable.landmark_icon)
            "park" -> context.getDrawable(R.drawable.park_icon)
            else -> {
                Log.d("OSMAPVIEW", "unknown location type: $locationType")
                context.getDrawable(R.drawable.baseline_location_on_24)
            }
        }
    }
}
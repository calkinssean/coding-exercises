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

    val name: String
        get() = attributes.firstOrNull { it.type == "name" }?.value ?: ""

    val description: String
        get() = attributes.firstOrNull { it.type == "description" }?.value ?: ""

    fun toMarker(context: Context, mapView: MapView): Marker {
        val marker = Marker(mapView)
        marker.position = position
        marker.icon = icon(context)
        return marker
    }

    val drawableId: Int
        get() = when (locationType) {
            "restaurant" -> R.drawable.restaurant_icon
            "bar" -> R.drawable.bar_icon
            "cafe" -> R.drawable.cafe_icon
            "museum" -> R.drawable.museum_icon
            "landmark" -> R.drawable.landmark_icon
            "park" -> R.drawable.park_icon
            else -> {
                Log.d("OSMMAPVIEW", "unknown location type: $locationType")
                R.drawable.baseline_location_on_24
            }
        }

    // OSMMap doesn't have compose support so we have to use drawables
    fun icon(context: Context): Drawable? = context.getDrawable(drawableId)
}
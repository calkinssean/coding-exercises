package com.example.codingchallenge.mapscreen.model

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.codingchallenge.R
import com.example.codingchallenge.ui.theme.Brown700
import com.example.codingchallenge.ui.theme.Cyan500
import com.example.codingchallenge.ui.theme.Green500
import com.example.codingchallenge.ui.theme.Orange500
import com.example.codingchallenge.ui.theme.Pink500
import com.example.codingchallenge.ui.theme.Purple500
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

    val locationType: String
        get() = attributes.firstOrNull { it.type == "location_type" }?.value ?: ""

    val name: String
        get() = attributes.firstOrNull { it.type == "name" }?.value ?: ""

    val description: String
        get() = attributes.firstOrNull { it.type == "description" }?.value ?: ""

    fun toMarker(context: Context, mapView: MapView): Marker {
        val marker = Marker(mapView)
        marker.position = position
        marker.icon = drawableIcon(context)
        return marker
    }

    // OSMMap doesn't have compose support so we have to use drawables :(
    fun drawableIcon(context: Context): Drawable? = when (locationType) {
        "restaurant" -> context.getDrawable(R.drawable.restaurant_icon)
        "bar" -> context.getDrawable(R.drawable.bar_icon)
        "cafe" -> context.getDrawable(R.drawable.cafe_icon)
        "museum" -> context.getDrawable(R.drawable.museum_icon)
        "landmark" -> context.getDrawable(R.drawable.landmark_icon)
        "park" -> context.getDrawable(R.drawable.park_icon)
        else -> {
            Log.d("OSMMAPVIEW", "unknown location type: $locationType")
            context.getDrawable(R.drawable.baseline_location_on_24)
        }
    }

    val iconId: Int
        get() = when(locationType) {
            "restaurant" -> R.drawable.ic_restaurant
            "bar" -> R.drawable.ic_bar
            "cafe" -> R.drawable.ic_cafe
            "museum" -> R.drawable.ic_museum
            "landmark" -> R.drawable.ic_landmark
            "park" -> R.drawable.ic_park
            else -> R.drawable.baseline_location_on_24
        }

    val color: Color
        get() = when(locationType) {
            "restaurant" -> Pink500
            "bar" -> Purple500
            "cafe" -> Brown700
            "museum" -> Orange500
            "landmark" -> Cyan500
            "park" -> Green500
            else -> Color.Black
        }
}
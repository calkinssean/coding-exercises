package com.example.codingchallenge.mapscreen.model

import android.content.Context
import android.graphics.drawable.Drawable
import android.icu.text.NumberFormat
import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.intl.Locale
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
import kotlin.text.format

data class Location(
    val attributes: List<Attribute>,
    val id: Int,
    val latitude: Double,
    val longitude: Double
) {
    @VisibleForTesting
    val position: GeoPoint
        get() = GeoPoint(latitude, longitude)

    val locationType: String
        get() = attributes.firstOrNull { it.type == "location_type" }?.value ?: ""

    val name: String
        get() = attributes.firstOrNull { it.type == "name" }?.value ?: ""

    val description: String
        get() = attributes.firstOrNull { it.type == "description" }?.value ?: ""

    val formattedRevenueString: String
        get() {
            val revenueMillions =
                attributes.firstOrNull { it.type == "estimated_revenue_millions" }?.value?.toDoubleOrNull()
                    ?: 0.0
            val revenue = revenueMillions * 1_000_000
            val currencyFormatter = NumberFormat.getCurrencyInstance()
            currencyFormatter.maximumFractionDigits = 0
            return currencyFormatter.format(revenue)
        }

    fun toMarker(context: Context, mapView: MapView, selected: Boolean = false): Marker {
        val marker = Marker(mapView)
        marker.position = position
        marker.icon = drawableIcon(context, selected)
        marker.infoWindow = null
        return marker
    }

    // OSMMap doesn't have compose support so we have to use drawables :(
    @VisibleForTesting
    fun drawableIcon(context: Context, selected: Boolean): Drawable? = when (locationType) {
        "restaurant" -> context.getDrawable(if (selected) R.drawable.restaurant_icon_selected else R.drawable.restaurant_icon)
        "bar" -> context.getDrawable(if (selected) R.drawable.bar_icon_selected else R.drawable.bar_icon)
        "cafe" -> context.getDrawable(if (selected) R.drawable.cafe_icon_selected else R.drawable.cafe_icon)
        "museum" -> context.getDrawable(if (selected) R.drawable.museum_icon_selected else R.drawable.museum_icon)
        "landmark" -> context.getDrawable(if (selected) R.drawable.landmark_icon_selected else R.drawable.landmark_icon)
        "park" -> context.getDrawable(if (selected) R.drawable.park_icon_selected else R.drawable.park_icon)
        else -> {
            Log.d("OSMMAPVIEW", "unknown location type: $locationType")
            context.getDrawable(R.drawable.baseline_location_on_24)
        }
    }

    val iconId: Int
        get() = when (locationType) {
            "restaurant" -> R.drawable.ic_restaurant
            "bar" -> R.drawable.ic_bar
            "cafe" -> R.drawable.ic_cafe
            "museum" -> R.drawable.ic_museum
            "landmark" -> R.drawable.ic_landmark
            "park" -> R.drawable.ic_park
            else -> R.drawable.baseline_location_on_24
        }

    val color: Color
        get() = when (locationType) {
            "restaurant" -> Pink500
            "bar" -> Purple500
            "cafe" -> Brown700
            "museum" -> Orange500
            "landmark" -> Cyan500
            "park" -> Green500
            else -> Color.Black
        }
}
package com.example.codingchallenge.mapscreen.model

import androidx.compose.ui.graphics.Color
import com.example.codingchallenge.R
import com.example.codingchallenge.ui.theme.Brown700
import com.example.codingchallenge.ui.theme.Cyan500
import com.example.codingchallenge.ui.theme.Green500
import com.example.codingchallenge.ui.theme.Orange500
import com.example.codingchallenge.ui.theme.Pink500
import com.example.codingchallenge.ui.theme.Purple500
import junit.framework.Assert.assertEquals
import org.junit.Test
import java.util.UUID
import kotlin.random.Random


class LocationTest {

    @Test
    fun `position creates GeoPoint`() {
        val location = Location(
            id = 0,
            latitude = Random.nextDouble(),
            longitude = Random.nextDouble(),
            attributes = listOf()
        )
        val position = location.position

        assert(position.latitude == location.latitude)
        assert(position.longitude == location.longitude)
    }

    @Test
    fun `locationType grabs correct value`() {
        val randomString = UUID.randomUUID().toString()
        val attributes = listOf(
            Attribute(type = "location_type", value = randomString),
            Attribute(type = "name", value = "name"),
            Attribute(type = "description", value = "description")
        )
        val location = Location(id = 0, latitude = 0.0, longitude = 0.0, attributes = attributes)
        assertEquals(randomString, location.locationType)
    }

    @Test
    fun `name grabs correct value`() {
        val randomString = UUID.randomUUID().toString()
        val attributes = listOf(
            Attribute(type = "location_type", value = "location_type"),
            Attribute(type = "name", value = randomString),
            Attribute(type = "description", value = "description")
        )
        val location = Location(id = 0, latitude = 0.0, longitude = 0.0, attributes = attributes)
        assertEquals(randomString, location.name)
    }

    @Test
    fun `description grabs correct value`() {
        val randomString = UUID.randomUUID().toString()
        val attributes = listOf(
            Attribute(type = "location_type", value = "location_type"),
            Attribute(type = "name", value = "name"),
            Attribute(type = "description", value = randomString)
        )
        val location = Location(id = 0, latitude = 0.0, longitude = 0.0, attributes = attributes)
        assertEquals(randomString, location.description)
    }

    @Test
    fun `iconId returns correct value`() {
        val location1 = Location(
            id = 0,
            latitude = 0.0,
            longitude = 0.0,
            attributes = listOf(Attribute(type = "location_type", value = "restaurant"))
        )
        assertEquals(R.drawable.ic_restaurant, location1.iconId)

        val location2 = Location(
            id = 0,
            latitude = 0.0,
            longitude = 0.0,
            attributes = listOf(Attribute(type = "location_type", value = "bar"))
        )
        assertEquals(R.drawable.ic_bar, location2.iconId)

        val location3 = Location(
            id = 0,
            latitude = 0.0,
            longitude = 0.0,
            attributes = listOf(Attribute(type = "location_type", value = "cafe"))
        )
        assertEquals(R.drawable.ic_cafe, location3.iconId)

        val location4 = Location(
            id = 0,
            latitude = 0.0,
            longitude = 0.0,
            attributes = listOf(Attribute(type = "location_type", value = "museum"))
        )
        assertEquals(R.drawable.ic_museum, location4.iconId)

        val location5 = Location(
            id = 0,
            latitude = 0.0,
            longitude = 0.0,
            attributes = listOf(Attribute(type = "location_type", value = "landmark"))
        )
        assertEquals(R.drawable.ic_landmark, location5.iconId)

        val location6 = Location(
            id = 0,
            latitude = 0.0,
            longitude = 0.0,
            attributes = listOf(Attribute(type = "location_type", value = "park"))
        )
        assertEquals(R.drawable.ic_park, location6.iconId)

        val location7 = Location(
            id = 0,
            latitude = 0.0,
            longitude = 0.0,
            attributes = listOf(Attribute(type = "location_type", value = "random"))
        )
        assertEquals(R.drawable.baseline_location_on_24, location7.iconId)
    }

    @Test
    fun `color returns correct value`() {
        val location1 = Location(
            id = 0,
            latitude = 0.0,
            longitude = 0.0,
            attributes = listOf(Attribute(type = "location_type", value = "restaurant"))
        )
        assertEquals(Pink500, location1.color)

        val location2 = Location(
            id = 0,
            latitude = 0.0,
            longitude = 0.0,
            attributes = listOf(Attribute(type = "location_type", value = "bar"))
        )
        assertEquals(Purple500, location2.color)

        val location3 = Location(
            id = 0,
            latitude = 0.0,
            longitude = 0.0,
            attributes = listOf(Attribute(type = "location_type", value = "cafe"))
        )
        assertEquals(Brown700, location3.color)

        val location4 = Location(
            id = 0,
            latitude = 0.0,
            longitude = 0.0,
            attributes = listOf(Attribute(type = "location_type", value = "museum"))
        )
        assertEquals(Orange500, location4.color)

        val location5 = Location(
            id = 0,
            latitude = 0.0,
            longitude = 0.0,
            attributes = listOf(Attribute(type = "location_type", value = "landmark"))
        )
        assertEquals(Cyan500, location5.color)

        val location6 = Location(
            id = 0,
            latitude = 0.0,
            longitude = 0.0,
            attributes = listOf(Attribute(type = "location_type", value = "park"))
        )
        assertEquals(Green500, location6.color)

        val location7 = Location(
            id = 0,
            latitude = 0.0,
            longitude = 0.0,
            attributes = listOf(Attribute(type = "location_type", value = "random"))
        )
        assertEquals(Color.Black, location7.color)
    }

}
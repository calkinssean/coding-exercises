package com.example.codingchallenge.mapscreen.model

import junit.framework.Assert.assertEquals
import org.junit.Test
import java.util.UUID


class MapScreenModelTest {

    @Test
    fun `locationTypes returns correct values`() {
        val randomString = UUID.randomUUID().toString()
        val locations = listOf(
            Location(
                id = 0, latitude = 0.0, longitude = 0.0, attributes = listOf(
                    Attribute("location_type", "restaurant"),
                    Attribute("location_type", "restaurant"),
                    Attribute("location_type", randomString),
                )
            )
        )
        val objectUnderTest = MapScreenModel(locations = locations)
        val locationTypes = objectUnderTest.locationTypes
        assertEquals(2, locationTypes.size)
        assert(locationTypes.map { it.value }.contains(randomString))
        assert(locationTypes.map { it.value }.contains("restaurant"))
    }

    @Test
    fun `filterByTypes returns locations with corresponding location_type attributes`() {
        val locations: MutableList<Location> = mutableListOf()
        locationTypes.forEach {
            for (i in 0.until(10)) {
                locations.add(locationWithLocationType(it))
            }
        }
        val shuffledLocationTypes = locationTypes.shuffled()
        val randomLocationAttribute1 = Attribute("location_type", shuffledLocationTypes.first())
        val randomLocationAttribute2 = Attribute("location_type", shuffledLocationTypes.last())
        val objectUnderTest = MapScreenModel(selectedLocationTypes = listOf(randomLocationAttribute1, randomLocationAttribute2))

        val filteredLocations = objectUnderTest.filterByTypes(locations)

        val firstAttributeLocations = locations.filter { it.attributes.contains(randomLocationAttribute1) }
        val secondAttributeLocations = locations.filter { it.attributes.contains(randomLocationAttribute2) }

        assert(filteredLocations.containsAll(firstAttributeLocations))
        assert(filteredLocations.containsAll(secondAttributeLocations))
        assertEquals(firstAttributeLocations.size + secondAttributeLocations.size, filteredLocations.size)
    }

    @Test
    fun `filterByTypes returns all locations list if no selected location_type attributes`() {
        val locations: MutableList<Location> = mutableListOf()
        locationTypes.forEach {
            for (i in 0.until(10)) {
                locations.add(locationWithLocationType(it))
            }
        }
        val objectUnderTest = MapScreenModel()

        val filteredLocations = objectUnderTest.filterByTypes(locations)
        assertEquals(locations, filteredLocations)
    }

    @Test
    fun `searchResults searches by name and location type`() {
        val randomString = UUID.randomUUID().toString()
        val locations = listOf(
            Location(id = 0, latitude = 0.0, longitude = 0.0, attributes = listOf(Attribute("name", randomString))),
            Location(id = 0, latitude = 0.0, longitude = 0.0, attributes = listOf(Attribute("location_type", randomString))),
            Location(id = 0, latitude = 0.0, longitude = 0.0, attributes = listOf(Attribute("name", UUID.randomUUID().toString()))),
            Location(id = 0, latitude = 0.0, longitude = 0.0, attributes = listOf(Attribute("location_type", UUID.randomUUID().toString()))),
        )
        val objectUnderTest = MapScreenModel(locations = locations, searchQuery = randomString)
        val searchResults = objectUnderTest.searchResults
        assertEquals(2, searchResults.size)
        assert(searchResults.map { it.name }.contains(randomString))
        assert(searchResults.map { it.locationType }.contains(randomString))
    }

    @Test
    fun `searchResults returns all locations if search query is empty`() {
        val locations = listOf(
            Location(id = 0, latitude = 0.0, longitude = 0.0, attributes = listOf(Attribute("name", UUID.randomUUID().toString()))),
            Location(id = 0, latitude = 0.0, longitude = 0.0, attributes = listOf(Attribute("location_type", UUID.randomUUID().toString()))),
            Location(id = 0, latitude = 0.0, longitude = 0.0, attributes = listOf(Attribute("name", UUID.randomUUID().toString()))),
            Location(id = 0, latitude = 0.0, longitude = 0.0, attributes = listOf(Attribute("location_type", UUID.randomUUID().toString()))),
        )
        val objectUnderTest = MapScreenModel(locations = locations, searchQuery = "")
        val searchResults = objectUnderTest.searchResults
        assertEquals(locations, searchResults)
    }

    private fun locationWithLocationType(locationType: String): Location {
        return Location(
            id = 0, latitude = 0.0, longitude = 0.0, attributes = listOf(
                Attribute("location_type", locationType)
            )
        )
    }

    private val locationTypes: List<String> = listOf(
        "restaurant",
        "museum",
        "cafe",
        "landmark",
        "park",
        "bar"
    )

}
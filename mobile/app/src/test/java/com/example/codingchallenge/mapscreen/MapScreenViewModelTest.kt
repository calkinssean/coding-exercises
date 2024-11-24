package com.example.codingchallenge.mapscreen

import com.example.codingchallenge.common.GenericError
import com.example.codingchallenge.common.LoadState
import com.example.codingchallenge.mapscreen.model.Attribute
import com.example.codingchallenge.mapscreen.model.Location
import com.example.codingchallenge.repositories.LocationsRepository
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.UUID
import kotlin.random.Random


class MapScreenViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)
    private val mockLocationsRepository: LocationsRepository = mock()
    private lateinit var objectUnderTest: MapScreenViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        objectUnderTest = MapScreenViewModel(mockLocationsRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onStart calls correct endpoints and calls correct endpoints on success`() =
        testScope.runTest {
            val locations = locations()
            whenever(mockLocationsRepository.getLocations()).thenReturn(locations)

            objectUnderTest.onStart()

            advanceUntilIdle()

            verify(mockLocationsRepository, times(1)).getLocations()
            val latestModel = objectUnderTest.latestModel
            assertEquals(locations, latestModel.locations)
            assertEquals(LoadState.None, latestModel.loadState)
            assertNull(latestModel.error)
        }

    @Test
    fun `onStart calls correct endpoints and calls correct endpoints on failure`() =
        testScope.runTest {
            whenever(mockLocationsRepository.getLocations()).thenThrow(RuntimeException("oops"))

            objectUnderTest.onStart()

            advanceUntilIdle()

            verify(mockLocationsRepository, times(1)).getLocations()
            val latestModel = objectUnderTest.latestModel
            assertEquals(GenericError, latestModel.error)
            assertEquals(LoadState.None, latestModel.loadState)
        }

    @Test
    fun `onRetry updates state and calls correct endpoints`() = testScope.runTest {
        whenever(mockLocationsRepository.getLocations()).thenReturn(listOf())

        objectUnderTest.mutableModel.update { it.copy(error = GenericError) }
        objectUnderTest.onRetry()

        advanceUntilIdle()

        verify(mockLocationsRepository, times(1)).getLocations()
        val latestModel = objectUnderTest.latestModel
        assertNull(latestModel.error)
    }

    @Test
    fun onSearchQueryUpdated() {
        val randomString = UUID.randomUUID().toString()
        objectUnderTest.onSearchQueryChanged(randomString)

        val latestModel = objectUnderTest.latestModel
        assertEquals(randomString, latestModel.searchQuery)
    }

    @Test
    fun `onLocationTypeSelected removes already selected location type`() {
        val locationType = Attribute(type = "location_type", value = "value")
        objectUnderTest.mutableModel.update { it.copy(selectedLocationTypes = listOf(locationType)) }

        objectUnderTest.onLocationTypeSelected(locationType)

        val latestModel = objectUnderTest.latestModel

        assert(latestModel.selectedLocationTypes.isEmpty())
    }

    @Test
    fun `onLocationTypeSelected adds location type`() {
        val locationType = Attribute(type = "location_type", value = "value")
        objectUnderTest.mutableModel.update { it.copy(selectedLocationTypes = listOf()) }

        objectUnderTest.onLocationTypeSelected(locationType)

        val latestModel = objectUnderTest.latestModel

        assertEquals(locationType, latestModel.selectedLocationTypes.first())
        assert(latestModel.selectedLocationTypes.contains(locationType))
    }

    @Test
    fun `onClearAllLocationTypesClicked clears selected location types`() {
        objectUnderTest.mutableModel.update {
            it.copy(
                selectedLocationTypes = listOf(
                    Attribute(type = "location_type", value = "value1"),
                    Attribute(type = "location_type", value = "value2"),
                    Attribute(type = "location_type", value = "value3")
                )
            )
        }
        objectUnderTest.onClearAllLocationTypesClicked()

        val latestModel = objectUnderTest.latestModel
        assert(latestModel.selectedLocationTypes.isEmpty())
    }

    @Test
    fun `onLocationSelected updates state correctly`() {
        val location = locations().first()
        objectUnderTest.onLocationSelected(location)

        val latestModel = objectUnderTest.latestModel

        assertEquals(location, latestModel.selectedLocation)
    }

    @Test
    fun `onLocationSelected sets location to null`() {
        val location = locations().first()
        objectUnderTest.mutableModel.update { it.copy(selectedLocation = location) }
        objectUnderTest.onLocationSelected(null)

        val latestModel = objectUnderTest.latestModel

        assertNull(latestModel.selectedLocation)
    }

    @Test
    fun `onSearchResultSelected updates state correctly`() {
        val location = locations().first()
        objectUnderTest.onSearchResultSelected(location)

        val latestModel = objectUnderTest.latestModel

        assertEquals(location, latestModel.selectedLocation)
        assert(latestModel.shouldPanToSelectedLocation)
    }

    @Test
    fun `didPanToSelectedLocation updates state correctly`() {
        objectUnderTest.mutableModel.update { it.copy(shouldPanToSelectedLocation = true) }
        objectUnderTest.didPanToSelectedLocation()

        val latestModel = objectUnderTest.latestModel
        assertFalse(latestModel.shouldPanToSelectedLocation)
    }

    @Test
    fun `onUpdateShouldRecenterMap updates state correctly`() {
        val expectedValue = Random.nextBoolean()
        objectUnderTest.onUpdateShouldRecenterMap(expectedValue)
        val latestModel = objectUnderTest.latestModel
        assertEquals(expectedValue, latestModel.shouldRecenterMap)
    }

    private fun locations(): List<Location> {
        val result: MutableList<Location> = mutableListOf()
        for (i in 0.until(Random.nextInt(1, 10))) {
            result.add(
                Location(
                    id = i,
                    latitude = Random.nextDouble(),
                    longitude = Random.nextDouble(),
                    attributes = attributes()
                )
            )
        }
        return result
    }


    private fun attributes(): List<Attribute> {
        val result: MutableList<Attribute> = mutableListOf()
        for (i in 0.until(Random.nextInt(1, 4))) {
            result.add(
                Attribute(
                    type = UUID.randomUUID().toString(),
                    value = UUID.randomUUID().toString()
                )
            )
        }
        return result
    }
}
package com.example.codingchallenge.repositories

import com.example.codingchallenge.api.LocationsApi
import com.example.codingchallenge.mapscreen.model.Location
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationsRepository @Inject constructor(
    private val locationsApi: LocationsApi
) {
    suspend fun getLocations(): List<Location> {
        return locationsApi.getLocations()
    }
}
package com.example.codingchallenge.domain.repository

import com.example.codingchallenge.data.remote.LocationsApi
import com.example.codingchallenge.domain.model.Location
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
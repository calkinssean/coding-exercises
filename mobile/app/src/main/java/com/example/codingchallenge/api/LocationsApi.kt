package com.example.codingchallenge.api

import com.example.codingchallenge.mapscreen.model.Location
import retrofit2.http.GET

interface LocationsApi {

    @GET("coding-exercises/master/mobile/map-locations/locations.json")
    suspend fun getLocations(): List<Location>

}
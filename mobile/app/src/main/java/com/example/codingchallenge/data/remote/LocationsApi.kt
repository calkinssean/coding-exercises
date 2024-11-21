package com.example.codingchallenge.data.remote

import com.example.codingchallenge.domain.model.Location
import retrofit2.http.GET

interface LocationsApi {

    @GET("coding-exercises/master/mobile/map-locations/locations.json")
    suspend fun getLocations(): List<Location>

}
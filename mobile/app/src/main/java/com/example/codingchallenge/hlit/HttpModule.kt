package com.example.codingchallenge.hlit

import com.example.codingchallenge.data.remote.LocationsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HttpModule {

    private const val BASE_URL = "https://raw.githubusercontent.com/calkinssean/"

    @Provides
    @Singleton
    fun provideLocationsApi(): LocationsApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LocationsApi::class.java)
    }

}
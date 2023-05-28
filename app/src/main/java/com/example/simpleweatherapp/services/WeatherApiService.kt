package com.example.simpleweatherapp.services

import com.example.simpleweatherapp.models.WeatherData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("weather")
    suspend fun getWeatherData(
        @Query("id") cityId: String,
        @Query("APPID") apiKey: String,
        @Query("units") units: String
    ): Response<WeatherData>
}
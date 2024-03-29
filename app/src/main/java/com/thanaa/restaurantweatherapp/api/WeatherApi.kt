package com.thanaa.restaurantweatherapp.api

import com.thanaa.restaurantweatherapp.weatherModel.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("forecast.json")
    suspend fun getWeather(
        @Query("key") key: String,
        @Query("q") latlon: String,
        @Query("days") days: String
    ): Response<WeatherResponse>
}
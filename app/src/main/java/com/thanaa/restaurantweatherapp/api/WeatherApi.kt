package com.thanaa.restaurantweatherapp.api

import com.thanaa.restaurantweatherapp.weatherModel.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("current.json")
    suspend fun getWeather(
        @Query("key") key: String,
        @Query("q") latlon: String
    ): Response<WeatherResponse>

}
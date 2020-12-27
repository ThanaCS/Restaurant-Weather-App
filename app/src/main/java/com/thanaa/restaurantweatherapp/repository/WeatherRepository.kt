package com.thanaa.restaurantweatherapp.repository

import com.thanaa.restaurantweatherapp.api.WeatherService
import com.thanaa.restaurantweatherapp.weatherModel.WeatherResponse
import retrofit2.Response

class WeatherRepository {

    private val weatherService = WeatherService()

    //weather Api
    suspend fun getWeather(latlon: String): Response<WeatherResponse> {
        return weatherService.getWeather(latlon)
    }


}
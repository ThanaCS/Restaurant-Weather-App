package com.thanaa.restaurantweatherapp.api

import com.thanaa.restaurantweatherapp.utils.Constants.Companion.Weather_API_KEY
import com.thanaa.restaurantweatherapp.utils.Constants.Companion.Weather_BASE_URL
import com.thanaa.restaurantweatherapp.utils.Constants.Companion.days
import com.thanaa.restaurantweatherapp.weatherModel.WeatherResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class WeatherService {

    private val retrofit = Retrofit.Builder()
        .baseUrl(Weather_BASE_URL).addConverterFactory(
            GsonConverterFactory.create()
        ).build().create(WeatherApi::class.java)

    suspend fun getWeather(latlon: String): Response<WeatherResponse> {
        return retrofit.getWeather(Weather_API_KEY, latlon, days)
    }
}
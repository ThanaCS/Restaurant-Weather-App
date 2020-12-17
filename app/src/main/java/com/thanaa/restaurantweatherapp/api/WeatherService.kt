package com.thanaa.restaurantweatherapp.api

import com.thanaa.restaurantweatherapp.weatherModel.WeatherResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val Weather_BASE_URL = "https://api.weatherapi.com/v1/"
private const val Weather_API_KEY = "47c22353a761496ebc523928201712"

class WeatherService {
    private val retrofit = Retrofit.Builder()
        .baseUrl(Weather_BASE_URL).addConverterFactory(
            GsonConverterFactory.create()
        ).build().create(WeatherApi::class.java)

    suspend fun getWeather(latlon: String): Response<WeatherResponse> {
        return retrofit.getWeather(Weather_API_KEY, latlon)
    }
}
package com.thanaa.restaurantweatherapp.repository

import com.thanaa.restaurantweatherapp.api.WeatherService
import com.thanaa.restaurantweatherapp.api.YelpService
import com.thanaa.restaurantweatherapp.model.RestaurantResponse
import com.thanaa.restaurantweatherapp.weatherModel.WeatherResponse
import retrofit2.Response

class Repository {

    private val weatherService = WeatherService()
    private val yelpService = YelpService()

    //weather Api
    suspend fun getWeather(latlon: String): Response<WeatherResponse> {
        return weatherService.getWeather(latlon)
    }

    //Yelp Api
    suspend fun getBusinesses(term: String, Location: String): Response<RestaurantResponse> {
        return yelpService.getBusinesses(term, Location)
    }

    suspend fun getBusinessesFromLanLon(
        term: String,
        lat: String,
        lon: String
    ): Response<RestaurantResponse> {
        return yelpService.getBusinessesFromLanLon(term, lat, lon)
    }


}
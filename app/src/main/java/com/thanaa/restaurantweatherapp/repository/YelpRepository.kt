package com.thanaa.restaurantweatherapp.repository

import com.thanaa.restaurantweatherapp.api.YelpService
import com.thanaa.restaurantweatherapp.model.RestaurantResponse
import retrofit2.Response

class YelpRepository {
    private val yelpService = YelpService()

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
package com.thanaa.restaurantweatherapp.api

import com.thanaa.restaurantweatherapp.model.RestaurantResponse
import com.thanaa.restaurantweatherapp.utils.Constants.Companion.BEARER
import com.thanaa.restaurantweatherapp.utils.Constants.Companion.YELP_API_KEY
import com.thanaa.restaurantweatherapp.utils.Constants.Companion.YELP_BASE_URL
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class YelpService {
    private val retrofit = Retrofit.Builder().baseUrl(YELP_BASE_URL).addConverterFactory(
        GsonConverterFactory.create()
    ).build().create(YelpApi::class.java)

    suspend fun getBusinesses(term: String, Location: String): Response<RestaurantResponse> {
        return retrofit.getBusinesses("$BEARER $YELP_API_KEY", term, Location)
    }

    suspend fun getBusinessesFromLanLon(
        term: String,
        lat: String,
        lon: String
    ): Response<RestaurantResponse> {
        return retrofit.getBusinessesFromLanLon("$BEARER $YELP_API_KEY", term, lat, lon)
    }

}
package com.thanaa.restaurantweatherapp.api

import com.thanaa.restaurantweatherapp.RestaurantModel.RestaurantResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface YelpApi {
    @GET("businesses/search")
    suspend fun getBusinesses(
        @Header("Authorization") authHeader: String,
        @Query("term") term: String,
        @Query("location") location: String
    ): Response<RestaurantResponse>
}
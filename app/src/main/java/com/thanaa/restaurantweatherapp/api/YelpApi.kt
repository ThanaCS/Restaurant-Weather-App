package com.thanaa.restaurantweatherapp.api

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface YelpApi {
    @GET("businesses/search")
    fun getBusinesses(
        @Header("Authorization")authHeader:String,
                         @Query("term")term :String,
                         @Query("location")location:String): Response<List<Any>>
}
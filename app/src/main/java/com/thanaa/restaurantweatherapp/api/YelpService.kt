package com.thanaa.restaurantweatherapp.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface YelpService {
    @GET("businesses/search")
    fun searchBusinesses(@Header("Authorization")authHeader:String,
                         @Query("term")searchTerm :String,
                         @Query("location")location:String): Call<Any>
}
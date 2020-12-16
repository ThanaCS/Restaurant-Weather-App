package com.thanaa.restaurantweatherapp.api

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
private const val YELP_BASE_URL = "https://api.yelp.com/v3/"
private const val YELP_API_KEY = "6U7509sPDxJrdiHmTcyxAU_yL4JDqyajyuluBvMRKV6qVlJxbjF850A8j_vbPPLotAYQyqFrDAUOcc7V_xyN_g_oI34dCKXe8s5OJi-wGiqj7NOijSC5eFjA-DbZX3Yx"
class YelpService {

   private val retrofit = Retrofit.Builder().baseUrl(YELP_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()
       .create(YelpApi::class.java)

    suspend fun getBusinesses(term:String, Location:String): Response<List<Any>> {
        return retrofit.getBusinesses("Bearer $YELP_API_KEY",term,Location)
    }

}
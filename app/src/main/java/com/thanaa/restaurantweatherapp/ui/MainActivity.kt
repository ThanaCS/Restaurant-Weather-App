package com.thanaa.restaurantweatherapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.thanaa.restaurantweatherapp.R
import com.thanaa.restaurantweatherapp.api.YelpService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val TAG = "MainActivity"
private const val YELP_BASE_URL = "https://api.yelp.com/v3/"
private const val API_KEY = "6U7509sPDxJrdiHmTcyxAU_yL4JDqyajyuluBvMRKV6qVlJxbjF850A8j_vbPPLotAYQyqFrDAUOcc7V_xyN_g_oI34dCKXe8s5OJi-wGiqj7NOijSC5eFjA-DbZX3Yx"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder().baseUrl(YELP_BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        val yelpService = retrofit.create(YelpService::class.java)
        yelpService.searchBusinesses("Bearer $API_KEY","Toast","London").enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                Log.i(TAG,"$response")
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.i(TAG,"OnFailure $t")
            }
        })
    }
}
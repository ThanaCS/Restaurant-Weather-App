package com.thanaa.restaurantweatherapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.thanaa.restaurantweatherapp.RestaurantModel.RestaurantResponse
import com.thanaa.restaurantweatherapp.api.YelpApi
import com.thanaa.restaurantweatherapp.databinding.FragmentHomeBinding
import com.thanaa.restaurantweatherapp.viewmodel.YelpViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {
    private val YELP_BASE_URL = "https://api.yelp.com/v3/"
    private val API_KEY =
        "6U7509sPDxJrdiHmTcyxAU_yL4JDqyajyuluBvMRKV6qVlJxbjF850A8j_vbPPLotAYQyqFrDAUOcc7V_xyN_g_oI34dCKXe8s5OJi-wGiqj7NOijSC5eFjA-DbZX3Yx"
    private val TAG = "HomeFragment"
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: YelpViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(YelpViewModel::class.java)

        val retrofit = Retrofit.Builder().baseUrl(YELP_BASE_URL).addConverterFactory(
            GsonConverterFactory.create()
        ).build()
        val api = retrofit.create(YelpApi::class.java)
        api.getBusinesses("Bearer $API_KEY", "Toast", "London")
            .enqueue(object : Callback<RestaurantResponse> {
                override fun onResponse(
                    call: Call<RestaurantResponse>,
                    response: Response<RestaurantResponse>
                ) {
                    Log.i(TAG, "${response.body()}")
                }

                override fun onFailure(call: Call<RestaurantResponse>, t: Throwable) {
                    Log.i(TAG, "OnFailure $t")
                }
            })
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
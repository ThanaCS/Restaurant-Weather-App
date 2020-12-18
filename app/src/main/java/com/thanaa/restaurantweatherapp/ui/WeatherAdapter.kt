package com.thanaa.restaurantweatherapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thanaa.restaurantweatherapp.R
import com.thanaa.restaurantweatherapp.weatherModel.WeatherResponse

class WeatherAdapter(private val weather: WeatherResponse) :

    RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        fun bind(foodItem: WeatherResponse) {

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_weather, parent, false)
        )
    }

    override fun getItemCount(): Int = 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


    }


}
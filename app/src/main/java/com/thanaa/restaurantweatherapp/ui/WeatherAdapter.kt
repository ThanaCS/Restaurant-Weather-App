package com.thanaa.restaurantweatherapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thanaa.restaurantweatherapp.R
import com.thanaa.restaurantweatherapp.weatherModel.Hour

class WeatherAdapter(private val hours: List<Hour>) :

    RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val timeText: TextView = view.findViewById(R.id.weatherTime)
        private val weatherIcon: ImageView = view.findViewById(R.id.weatherIcon)
        fun bind(hours: Hour) {
            timeText.text = hours.time.split(' ')[1] + " "
            Glide.with(weatherIcon)
                .load("https:${hours.condition.icon}")
                .into(weatherIcon)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_weather, parent, false)
        )
    }

    override fun getItemCount(): Int = hours.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hourItem = hours[position]
        holder.bind(hourItem)

    }


}
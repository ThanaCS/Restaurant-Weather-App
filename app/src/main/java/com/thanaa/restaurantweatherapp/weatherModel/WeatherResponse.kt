package com.thanaa.restaurantweatherapp.weatherModel


data class WeatherResponse(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)

data class Current(
    val condition: Condition,
    val is_day: Int,
    val last_updated: String,
    val temp_c: Double,
    val temp_f: Double
)

data class Forecast(
    val forecastday: List<Forecastday>
)

data class Location(
    val country: String,
    val lat: Double,
    val localtime: String,
    val lon: Double,
    val name: String,
    val region: String,
    val tz_id: String
)

data class Condition(
    val code: Int,
    val icon: String,
    val text: String
)

data class Forecastday(
    val date: String,
    val day: Day,
    val hour: List<Hour>
)

data class Day(
    val condition: Condition
)

data class Hour(
    val chance_of_rain: String,
    val chance_of_snow: String,
    val condition: Condition,
    val is_day: Int,
    val temp_c: Double,
    val temp_f: Double,
    val time: String,
)
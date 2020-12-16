package com.thanaa.restaurantweatherapp.RestaurantModel

data class RestaurantResponse(
    val businesses: List<Businesses>,
    val region: Region,
    val total: Int
)
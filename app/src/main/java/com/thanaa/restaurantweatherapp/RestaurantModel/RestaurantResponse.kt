package com.thanaa.restaurantweatherapp.RestaurantModel

data class RestaurantResponse(
    val businesses: List<Businesse>,
    val region: Region,
    val total: Int
)
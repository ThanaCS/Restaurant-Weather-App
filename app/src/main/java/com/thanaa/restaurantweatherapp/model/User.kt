package com.thanaa.restaurantweatherapp.model

data class User(
    val id: String = "",
    val username: String = "",
    val email: String = "",
    val image: String = "",
    var score: Int = 0
)
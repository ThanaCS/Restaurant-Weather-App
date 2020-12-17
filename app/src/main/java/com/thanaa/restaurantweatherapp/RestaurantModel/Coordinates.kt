package com.thanaa.restaurantweatherapp.RestaurantModel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Coordinates(
    val latitude: Double,
    val longitude: Double
) : Parcelable
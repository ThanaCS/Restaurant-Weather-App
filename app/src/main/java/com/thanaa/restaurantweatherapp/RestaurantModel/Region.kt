package com.thanaa.restaurantweatherapp.RestaurantModel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Region(
    val center: Center
) : Parcelable
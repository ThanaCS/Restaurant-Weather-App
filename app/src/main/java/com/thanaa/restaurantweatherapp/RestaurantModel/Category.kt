package com.thanaa.restaurantweatherapp.RestaurantModel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(
    val alias: String,
    val title: String
) : Parcelable
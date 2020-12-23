package com.thanaa.restaurantweatherapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CountryItem(
    var countryName: String,
    var flagImage: Int
) : Parcelable
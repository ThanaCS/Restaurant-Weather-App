package com.thanaa.restaurantweatherapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

data class RestaurantResponse(
    val businesses: List<Businesses>,
    val region: Region,
    val total: Int
)

@Entity(tableName = "businesses_table")
@Parcelize
data class Businesses(
    @PrimaryKey(autoGenerate = true)
    var BusinessesId: Int? = null,
    val alias: String,
    val categories: List<Category>,
    val coordinates: Coordinates,
    val display_phone: String,
    val distance: Double,
    val id: String,
    val image_url: String,
    val is_closed: Boolean,
    val location: Location,
    val name: String,
    val phone: String,
    val price: String,
    val rating: Double,
    val review_count: Int,
    val transactions: List<String>,
    val url: String
) : Parcelable

@Parcelize
data class Region(
    val center: Center
) : Parcelable

@Parcelize
data class Location(
    val address1: String,
    val address2: String,
    val address3: String,
    val city: String,
    val country: String,
    val display_address: List<String>,
    val state: String,
    val zip_code: String
) : Parcelable

@Parcelize
data class Coordinates(
    val latitude: Double,
    val longitude: Double
) : Parcelable

@Parcelize
data class Center(
    val latitude: Double,
    val longitude: Double
) : Parcelable

@Parcelize
data class Category(
    val alias: String,
    val title: String
) : Parcelable


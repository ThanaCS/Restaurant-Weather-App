package com.thanaa.restaurantweatherapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "bookmark_table")
@Parcelize
data class Bookmark(
    @PrimaryKey var id: String,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val image_url: String,
    val category: String?
) : Parcelable


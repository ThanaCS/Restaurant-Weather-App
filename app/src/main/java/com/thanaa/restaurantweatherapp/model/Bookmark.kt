package com.thanaa.restaurantweatherapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "bookmark_table")
@Parcelize
data class Bookmark(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val name: String,
    val phone: String,
    val latitude: Double,
    val longitude: Double,
    val image_url: String,
) : Parcelable


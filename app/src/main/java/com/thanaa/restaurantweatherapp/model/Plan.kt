package com.thanaa.restaurantweatherapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity(tableName = "plan_table")
@Parcelize
data class Plan(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var description: String,
    var date: Date = Date(),
    var color: Color,
    var location: String,
    var icon: Int?
) : Parcelable
package com.thanaa.restaurantweatherapp.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thanaa.restaurantweatherapp.model.Category
import com.thanaa.restaurantweatherapp.model.Coordinates
import com.thanaa.restaurantweatherapp.model.Location

class BusinessTypeConverter {
    var gson = Gson()

    @TypeConverter
    fun CoordinatesToString(coordinates: Coordinates): String {
        return gson.toJson(coordinates)
    }

    @TypeConverter
    fun stringToCoordinates(data: String): Coordinates {
        val listType = object : TypeToken<Coordinates>() {}.type //token
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun businessToLocation(business: Location): String {
        return gson.toJson(business)
    }

    @TypeConverter
    fun stringToLocation(data: String): Location {
        val listType = object : TypeToken<Location>() {}.type //token
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun businessToCategories(category: List<Category>): String {
        return gson.toJson(category)
    }

    @TypeConverter
    fun stringToCategories(data: String): List<Category> {
        val listType = object : TypeToken<List<Category>>() {}.type //token
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun businessToTransactions(category: List<String>): String {
        return gson.toJson(category)
    }

    @TypeConverter
    fun stringToTransactions(data: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type //token
        return gson.fromJson(data, listType)
    }


}
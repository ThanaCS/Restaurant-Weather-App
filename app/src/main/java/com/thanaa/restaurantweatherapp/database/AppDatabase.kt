package com.thanaa.restaurantweatherapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.thanaa.restaurantweatherapp.model.Businesses
import com.thanaa.restaurantweatherapp.model.Plan

@Database(entities = [Businesses::class, Plan::class], version = 1)
@TypeConverters(TypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun BusinessDao(): BusinessDao
    abstract fun PlanDao(): PlanDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {//to not create another instance
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "BusinessDatabase.db"
                ).build()
                INSTANCE = instance
                return instance
            }

        }


    }
}

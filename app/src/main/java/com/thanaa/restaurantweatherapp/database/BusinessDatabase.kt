package com.thanaa.restaurantweatherapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.thanaa.restaurantweatherapp.model.Businesses

@Database(entities = [Businesses::class], version = 1)
@TypeConverters(BusinessTypeConverter::class)
abstract class BusinessDatabase : RoomDatabase() {
    abstract fun BusinessDao(): BusinessDao

    companion object {

        @Volatile
        private var INSTANCE: BusinessDatabase? = null

        fun getDatabase(context: Context): BusinessDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {//to not create another instance
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BusinessDatabase::class.java,
                    "BusinessDatabase.db"
                ).build()
                INSTANCE = instance
                return instance
            }

        }


    }
}

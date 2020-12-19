package com.thanaa.restaurantweatherapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.thanaa.restaurantweatherapp.model.Businesses

@Database(
    entities = [Businesses::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(BusinessTypeConverter::class)
abstract class BusinessDatabase : RoomDatabase() {

    abstract fun BusinessDao(): BusinessDao

    companion object {
        @Volatile
        private var instance: BusinessDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                BusinessDatabase::class.java,
                "BusinessDatabase_db.db"
            ).build()
    }


}

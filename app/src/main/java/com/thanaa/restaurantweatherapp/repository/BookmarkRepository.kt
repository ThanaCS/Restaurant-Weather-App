package com.thanaa.restaurantweatherapp.repository

import androidx.lifecycle.LiveData
import com.thanaa.restaurantweatherapp.database.AppDatabase
import com.thanaa.restaurantweatherapp.model.Bookmark


class BookmarkRepository(private val database: AppDatabase) {
    val getAllData: LiveData<List<Bookmark>> = database.BookmarkDao().getAllBookmark()

    suspend fun insertData(bookmark: Bookmark) {
        database.BookmarkDao().insertBookmark(bookmark)
    }

    suspend fun updateData(plan: Bookmark) {
        database.BookmarkDao().updatePlan(plan)
    }

    suspend fun deleteItem(bookmark: Bookmark) {
        database.BookmarkDao().deleteBookmark(bookmark)
    }

    suspend fun deleteAll() {
        database.BookmarkDao().deleteAllBookmark()
    }
}
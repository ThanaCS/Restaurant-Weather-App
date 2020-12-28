package com.thanaa.restaurantweatherapp.repository

import androidx.lifecycle.LiveData
import com.thanaa.restaurantweatherapp.database.BookmarkDao
import com.thanaa.restaurantweatherapp.model.Bookmark


class BookmarkRepository(private val bookmarkDao: BookmarkDao) {
    val getAllData: LiveData<List<Bookmark>> = bookmarkDao.getAllBookmark()

    suspend fun insertData(bookmark: Bookmark) {
        bookmarkDao.insertBookmark(bookmark)
    }

    suspend fun updateData(plan: Bookmark) {
        bookmarkDao.updatePlan(plan)
    }

    suspend fun deleteItem(bookmark: Bookmark) {
        bookmarkDao.deleteBookmark(bookmark)
    }

    suspend fun deleteAll() {
        bookmarkDao.deleteAllBookmark()
    }
}
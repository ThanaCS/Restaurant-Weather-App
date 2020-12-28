package com.thanaa.restaurantweatherapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.thanaa.restaurantweatherapp.model.Bookmark

@Dao
interface BookmarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: Bookmark)

    @Update
    suspend fun updatePlan(bookmark: Bookmark)

    @Query("SELECT * FROM bookmark_table ")
    fun getAllBookmark(): LiveData<List<Bookmark>>

    @Delete
    suspend fun deleteBookmark(bookmark: Bookmark)

    @Query("DELETE FROM bookmark_table")
    suspend fun deleteAllBookmark()

}
package com.thanaa.restaurantweatherapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.thanaa.restaurantweatherapp.database.AppDatabase
import com.thanaa.restaurantweatherapp.model.Bookmark
import com.thanaa.restaurantweatherapp.repository.BookmarkRepository
import kotlinx.coroutines.launch

class BookmarkViewModel(application: Application) : AndroidViewModel(application) {
    private val bookmarkDao = AppDatabase.getDatabase(application).BookmarkDao()
    private val repository: BookmarkRepository
    val getAllData: LiveData<List<Bookmark>>

    val emptyDatabase: MutableLiveData<Boolean> = MutableLiveData(true)

    init {
        repository = BookmarkRepository(bookmarkDao)
        getAllData = repository.getAllData

    }

    fun insertData(bookmark: Bookmark) {
        viewModelScope.launch {
            repository.insertData(bookmark)
        }
    }

    fun deleteItem(bookmark: Bookmark) {
        viewModelScope.launch {
            repository.deleteItem(bookmark)
        }
    }

    fun updateData(bookmark: Bookmark) {
        viewModelScope.launch {
            repository.updateData(bookmark)
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }


    fun checkIfDatabaseEmpty(bookmark: List<Bookmark>) {
        emptyDatabase.value = bookmark.isEmpty()
    }

}
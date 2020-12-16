package com.thanaa.restaurantweatherapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class YelpViewModelTest {

    private lateinit var viewModel: YelpViewModel

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = YelpViewModel()
    }

    @Test
    fun `insert valid lan and long , returns result `() {
        viewModel.getBusinesses("toast", "France")
        val value = viewModel.businessesLiveData.getOrAwaitValue()
        print(value)

    }
}
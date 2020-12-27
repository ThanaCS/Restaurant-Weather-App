package com.thanaa.restaurantweatherapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class YelpViewModelTest {
    private lateinit var viewModel: YelpViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = YelpViewModel()
    }

    @Test
    fun `insert a valid term and a valid location , should return a result`() {
        viewModel.getBusinesses("Pizza", "London")
        val value = viewModel.businessesLiveData.getOrAwaitValue()
        print(value)
        assert(value[0].name.isNotBlank())
    }

    @Test
    fun `insert a valid term and an empty location , should return a null`() {
        viewModel.getBusinesses("Pizza", "")
        val value = viewModel.businessesLiveData.getOrAwaitVal()
        assertThat(value).isEqualTo(null)
    }

    @Test
    fun `insert a empty term and an empty location , should return a null`() {
        viewModel.getBusinesses("", "")
        val value = viewModel.businessesLiveData.getOrAwaitVal()
        assertThat(value).isEqualTo(null)
    }

}
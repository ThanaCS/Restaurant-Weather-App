package com.thanaa.restaurantweatherapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class WeatherViewModelTest {
    private lateinit var viewModel: WeatherViewModel

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = WeatherViewModel()
    }

    @Test
    fun `insert a valid lat and a valid lon , should return a result `() {
        viewModel.getWeather("0.0,0.0")
        val value = viewModel.weatherLiveData.getOrAwaitValue()
        print(value)
    }
    //


}
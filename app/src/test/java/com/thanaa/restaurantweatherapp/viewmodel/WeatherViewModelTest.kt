package com.thanaa.restaurantweatherapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class WeatherViewModelTest {
    private lateinit var viewModel: WeatherViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = WeatherViewModel()
    }

    @Test
    fun `insert a valid lat and a valid lon , should return a result`() {
        viewModel.getWeather("-12.234,44.00")
        val value = viewModel.weatherLiveData.getOrAwaitValue()
        print(value)
        assert(value.location.country.isNotBlank())
    }

    @Test
    fun `insert an empty lat and an empty lon , should return a null`() {
        viewModel.getWeather("")
        val value = viewModel.weatherLiveData.getOrAwaitVal()
        print(value)
        assertThat(value).isEqualTo(null)
    }

    @Test
    fun `insert invalid lat and lon , should return a null`() {
        viewModel.getWeather("0,0")
        val value = viewModel.weatherLiveData.getOrAwaitVal()
        print(value)
        assertThat(value).isEqualTo(null)
    }

    @Test
    fun `insert invalid lat and valid lon, should return a null`() {
        viewModel.getWeather("0,44.00")
        val value = viewModel.weatherLiveData.getOrAwaitVal()
        print(value)
        assertThat(value).isEqualTo(null)
    }

    @Test
    fun `insert valid lat and valid lon, should return a null`() {
        viewModel.getWeather("44.00,")
        val value = viewModel.weatherLiveData.getOrAwaitVal()
        print(value)
        assertThat(value).isEqualTo(null)
    }


}
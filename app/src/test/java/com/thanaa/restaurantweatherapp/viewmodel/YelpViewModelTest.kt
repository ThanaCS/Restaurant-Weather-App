package com.thanaa.restaurantweatherapp.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class YelpViewModelTest {
    lateinit var viewModel: YelpViewModel
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = YelpViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun testFooWithLaunch() = runBlockingTest {
        foo()

    }

    fun CoroutineScope.foo() {
        viewModel.getBusinesses("CAKE", "UK")
        launch {
            val value = viewModel.businessesLiveData.getOrAwaitValue()
            print(value)


        }
    }

}
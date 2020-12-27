package com.thanaa.restaurantweatherapp.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.thanaa.restaurantweatherapp.model.Plan
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class PlanDaoTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var dao: PlanDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.PlanDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertPlan() = runBlocking {
        val plan = Plan(0, "title", "description", Date(), 2, "london", 2)
        dao.insertPlan(plan)
        val data = dao.getAllData().getOrAwaitValue()
        assert(data.isNotEmpty())
    }

    @Test
    fun deletePlan() = runBlocking {
        val plan = Plan(0, "title", "description", Date(), 2, "london", 2)
        dao.deleteItem(plan)
        val data = dao.getAllData().getOrAwaitValue()
        assert(data.isEmpty())
    }


}
package com.example.albertsons.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.albertsons.model.CatFactInfo
import com.example.albertsons.retrofit.RetrofitService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class FactsViewModelTest {
    private val retrofitService: RetrofitService = mockk()
    private lateinit var factsViewModel: FactsViewModel
    private val catFact1:String = "Cats bury their faces to cover their trails from predators"
    private val catFact2:String = "Jaguars are the only big cats that don't roar"

    @ExperimentalCoroutinesApi
    private val testDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        factsViewModel = FactsViewModel(retrofitService)
    }

    @Test
    fun getCatFactData_providesOneFact_updatesCatFactLiveDataWithCatFact() {
        val catFactInfo = mockk<CatFactInfo>()
        every { catFactInfo.catFact } returns listOf(catFact1)
        runTest {
            coEvery { retrofitService.load(any())} answers { catFactInfo }
            factsViewModel.getCatFactData()
            factsViewModel.errorLiveData.value?.let { Assert.assertFalse(it) }
            Assert.assertNotNull(factsViewModel.catFactLiveData.value)
            Assert.assertEquals(factsViewModel.catFactLiveData.value, catFact1)
        }
    }

    @Test
    fun getCatFactData_providesMultipleFacts_updatesCatFactLiveDataWithRandomCatFact() {
        val catFactInfo = mockk<CatFactInfo>()
        val list = listOf(catFact1, catFact2, catFact1, catFact2)
        every { catFactInfo.catFact } returns list
        runTest {
            coEvery { retrofitService.load(any())} answers { catFactInfo }
            factsViewModel.getCatFactData()
            factsViewModel.errorLiveData.value?.let { Assert.assertFalse(it) }
            Assert.assertNotNull(factsViewModel.catFactLiveData.value)
           Assert.assertTrue(list.contains(factsViewModel.catFactLiveData.value))
        }
    }

    @Test
    fun getCatFactData_emptyCatFactsData_updatesErrorLiveDataWithTrue() {
        val catFactInfo = mockk<CatFactInfo>()
        val list = emptyList<String>()
        every { catFactInfo.catFact } returns list
        runTest {
            coEvery { retrofitService.load(any())} answers { catFactInfo }
            factsViewModel.getCatFactData()
            factsViewModel.errorLiveData.value?.let { Assert.assertTrue(it) }
        }
    }
}
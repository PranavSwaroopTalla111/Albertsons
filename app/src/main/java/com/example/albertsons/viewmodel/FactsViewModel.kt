package com.example.albertsons.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.albertsons.model.CatFactInfo
import com.example.albertsons.retrofit.RetrofitService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random
import kotlin.random.nextInt

@HiltViewModel
class FactsViewModel @Inject constructor(private val retrofitService: RetrofitService) :
    ViewModel() {
    private val catFactDataMutableLiveData = MutableLiveData<String>()
    val catFactLiveData: LiveData<String> = catFactDataMutableLiveData

    private val errorMutableLiveData = MutableLiveData<Boolean>()
    val errorLiveData: LiveData<Boolean> = errorMutableLiveData

    fun getCatFactData() {
        viewModelScope.launch {
            val data: CatFactInfo = retrofitService.load(Random.nextInt(1..5))
            data.catFact.let {
                if (it.isEmpty()) errorMutableLiveData.value = true
                else catFactDataMutableLiveData.value = it.random()
            }
        }
    }
}
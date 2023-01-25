package com.example.albertsons.retrofit

import com.example.albertsons.model.CatFactInfo
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET(".")
    suspend fun load(@Query("count") count: Int): CatFactInfo
}
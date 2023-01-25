package com.example.albertsons.retrofit

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RetrofitHelper {

    private const val CAT_FACT_BASE_URL = "https://meowfacts.herokuapp.com/"

    @Provides
    fun getRetrofitServiceInstance(): RetrofitService =
        Retrofit.Builder().baseUrl(CAT_FACT_BASE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(RetrofitService::class.java)
}

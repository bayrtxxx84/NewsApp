package com.example.adoptame.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitResponse {

    val APIKEY: String = "53525cdf7be148feac311794803b6c78"
    private val BASE_URL_NEWS = "https://newsapi.org/"

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
package com.example.adoptame.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitAPI {

    const val API_NEWS_CATCHER = "epUYga1Yo-ws1ON49fF4YlFCqIPAByTaVitXEkFbpBY"
    const val API_NEWS_API = "53525cdf7be148feac311794803b6c78"

    fun getNewsApi(): Retrofit {
        return Retrofit.Builder().baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getNewsCatcher(): Retrofit {
        return Retrofit.Builder().baseUrl("https://api.newscatcherapi.com/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}
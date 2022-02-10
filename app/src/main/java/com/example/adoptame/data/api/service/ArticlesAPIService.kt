package com.example.adoptame.data.api.service

import com.example.adoptame.data.api.entidades.NewsApiEntities
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ArticlesAPIService {

    @GET("/v2/top-headlines")
    suspend fun getAllNewsByCountryAndCategory(
        @Query("apikey") apikey: String,
        @Query("country") country: String,
        @Query("category") category: String
    ): Response<NewsApiEntities>

}
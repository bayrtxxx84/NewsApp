package com.example.adoptame.data.api.service

import com.example.adoptame.data.api.RetrofitAPI
import com.example.adoptame.data.api.entidades.newsApi.NewsApiEntity
import com.example.adoptame.data.api.entidades.newsCatcher.NewsCatch
import retrofit2.Response
import retrofit2.http.*

interface NewsService {

    @GET
    suspend fun getAllNewsByCountryAndCategory(@Url url: String): Response<NewsApiEntity>

    @GET("/v2/top-headlines")
    suspend fun getAllNewsByCategoryPage(
        @Query("category") category: String,
        @Query("page") page: Int,
        @Query("apikey") apikey: String = RetrofitAPI.API_NEWS_API,
    ): Response<NewsApiEntity>


    @Headers("x-api-key: epUYga1Yo-ws1ON49fF4YlFCqIPAByTaVitXEkFbpBY")
    @GET
    suspend fun getAllCatchNewsCriterioPage(@Url url: String): Response<NewsCatch>
}
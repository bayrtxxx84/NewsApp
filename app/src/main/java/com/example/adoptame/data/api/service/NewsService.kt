package com.example.adoptame.data.api.service

import com.example.adoptame.data.api.entidades.ArticlesEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface NewsService {

    @GET
    suspend fun getAllNewsByCountryAndCategory(@Url url:String): Response<ArticlesEntity>

}
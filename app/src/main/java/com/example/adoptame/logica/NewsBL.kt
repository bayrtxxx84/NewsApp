package com.example.adoptame.logica

import android.content.Context
import com.example.adoptame.casosUso.NewsUseCase
import com.example.adoptame.database.NewsDataBase
import com.example.adoptame.database.entidades.NewsEntity
import com.example.adoptame.utils.Adoptame

class NewsBL() {

    suspend fun getNewsList(): List<NewsEntity> {
        return NewsUseCase().getAllNews()
    }

    suspend fun getOneNews(): NewsEntity {
        val r = (0..3).random()
        return NewsUseCase().getAllNews()[r]
    }

    suspend fun checkIsSaved(id: Int): Boolean {
        val n = NewsUseCase().getOneNews(id)
        return (n != null)
    }

    suspend fun getFavoritesNews(): List<NewsEntity> {
        return NewsUseCase().getFavoritesNews()
    }

    suspend fun saveNewFavNews(news: NewsEntity) {
        NewsUseCase().saveNewFavNews(news)
    }

    suspend fun deleteNewFavNews(news: NewsEntity) {
        NewsUseCase().deleteNewFavNews(news)
    }

}
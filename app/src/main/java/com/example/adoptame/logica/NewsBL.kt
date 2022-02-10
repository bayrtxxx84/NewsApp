package com.example.adoptame.logica

import com.example.adoptame.casosUso.NewsUseCase
import com.example.adoptame.controladores.NewsController
import com.example.adoptame.database.entidades.NewsEntity
import com.example.adoptame.utils.EnumNews

class NewsBL() {

    suspend fun getNewsList(category: String, page: Int): List<NewsEntity> {
        return NewsUseCase().getAllNewsApi(category, page)
    }

    suspend fun getNewsCatchList(query: String, page: Int): List<NewsEntity> {
        return NewsUseCase().getAllNewsCatchApi(query, page)
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
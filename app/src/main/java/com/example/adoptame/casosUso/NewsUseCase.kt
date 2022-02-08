package com.example.adoptame.casosUso

import android.content.Context
import com.example.adoptame.data.api.RetrofitAPI
import com.example.adoptame.data.api.entidades.toNewsEntity
import com.example.adoptame.data.api.service.NewsService
import com.example.adoptame.database.entidades.NewsEntity
import com.example.adoptame.utils.Adoptame

class NewsUseCase {

    suspend fun getAllNews(): List<NewsEntity> {
        var resp: MutableList<NewsEntity> = ArrayList<NewsEntity>()
        val service = RetrofitAPI.getNewsApi().create(NewsService::class.java)
        val call =
            service.getAllNewsByCountryAndCategory("top-headlines?country=us&category=business&apiKey=53525cdf7be148feac311794803b6c78")

        resp = if (call.isSuccessful) {
            val body = call.body()
            body!!.articles.map {
                it.toNewsEntity()
            } as MutableList<NewsEntity>

        } else {
            ArrayList<NewsEntity>()
        }
        return resp
    }

    suspend fun getFavoritesNews(): List<NewsEntity> {
        val db = Adoptame.getDatabase()
        return db.newsDao().getAllNews()
    }

    suspend fun saveNewFavNews(news: NewsEntity) {
        Adoptame.getDatabase().newsDao().insertNews(news)
    }

    suspend fun deleteNewFavNews(news: NewsEntity) {
        Adoptame.getDatabase().newsDao().deleteNewsById(news.id)
    }

    suspend fun getOneNews(id: Int): NewsEntity {
        return Adoptame.getDatabase().newsDao().getNewsById(id)
    }

}
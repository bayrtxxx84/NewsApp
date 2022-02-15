package com.example.adoptame.casosUso

import com.example.adoptame.data.api.RetrofitAPI
import com.example.adoptame.data.api.entidades.newsApi.toNewsEntity
import com.example.adoptame.data.api.entidades.newsCatcher.toNewsEntity
import com.example.adoptame.data.api.service.NewsService
import com.example.adoptame.database.entidades.NewsEntity
import com.example.adoptame.utils.Adoptame

class NewsUseCase {

    suspend fun getAllNewsApi(
        category: String,
        page: Int,
    ): List<NewsEntity> {

        val service = RetrofitAPI.getNewsApi().create(NewsService::class.java)
        val call = service.getAllNewsByCategoryPage(category, page)
        val resp = if (call.isSuccessful) {
            return call.body()!!.articles.map {
                it.toNewsEntity()
            }
        } else (ArrayList<NewsEntity>())
        return resp
    }


    suspend fun getAllNewsCatchApi(
        query: String,
        page: Int,
    ): List<NewsEntity> {

        val service = RetrofitAPI.getNewsCatcher().create(NewsService::class.java)
        val s = "search?q=$query&page=$page"
        val call = service.getAllCatchNewsCriterioPage(s)
        println(call.code())
        val resp = if (call.isSuccessful) {
            return call.body()!!.articles.map {
                it.toNewsEntity()
            }
        } else (ArrayList<NewsEntity>())
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

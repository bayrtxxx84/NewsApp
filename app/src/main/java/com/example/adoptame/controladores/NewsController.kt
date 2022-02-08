package com.example.adoptame.controladores

import com.example.adoptame.database.entidades.NewsEntity
import com.example.adoptame.logica.NewsBL

class NewsController {

    suspend fun getOneNews(): NewsEntity {
        return NewsBL().getOneNews()
    }

    suspend fun checkIsSaved(id:Int): Boolean {
        return NewsBL().checkIsSaved(id)
    }

    suspend fun saveFavNews(news: NewsEntity){
        NewsBL().saveNewFavNews(news)
    }

    suspend fun deleteFavNews(news: NewsEntity){
        NewsBL().deleteNewFavNews(news)
    }

}
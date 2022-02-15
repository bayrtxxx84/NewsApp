package com.example.adoptame.controladores

import com.example.adoptame.R
import com.example.adoptame.database.entidades.NewsEntity
import com.example.adoptame.logica.NewsBL

class NewsController {

    suspend fun getNews(
        category: String,
        page: Int,
        apiType: List<String>
    ): List<NewsEntity> {
        var ret: ArrayList<NewsEntity> = ArrayList<NewsEntity>()
        apiType.forEach {
            val item = when (it) {
                R.string.newsapi.toString() -> {
                    NewsBL().getNewsList(category.toString(), page)
                }
                R.string.catchnewsapi.toString() -> {
                    NewsBL().getNewsCatchList(category.toString(), page)
                }
                else -> {
                    listOf<NewsEntity>()
                }
            }
            ret.addAll(item)
        }
        ret.shuffle()
        return ret
    }

    suspend fun saveFavNews(news: NewsEntity) {
        NewsBL().saveNewFavNews(news)
    }

    suspend fun deleteFavNews(news: NewsEntity) {
        NewsBL().deleteNewFavNews(news)
    }

}
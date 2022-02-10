package com.example.adoptame.controladores

import com.example.adoptame.database.entidades.NewsEntity
import com.example.adoptame.logica.NewsBL
import com.example.adoptame.utils.EnumNews

class NewsController {

    suspend fun getNews(category: String, page: Int, apiType: EnumNews.APITypes): List<NewsEntity> {
        return when (apiType) {
            EnumNews.APITypes.NewsApi ->
                NewsBL().getNewsList(category.toString(), page)
            EnumNews.APITypes.NewsCatcherApi ->
                NewsBL().getNewsCatchList(category.toString(), page)
        }
    }

    suspend fun saveFavNews(news: NewsEntity) {
        NewsBL().saveNewFavNews(news)
    }

    suspend fun deleteFavNews(news: NewsEntity) {
        NewsBL().deleteNewFavNews(news)
    }

}
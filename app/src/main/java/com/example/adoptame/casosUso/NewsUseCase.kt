package com.example.adoptame.casosUso

import android.content.Context
import com.example.adoptame.database.entidades.NewsEntity
import com.example.adoptame.utils.Adoptame

class NewsUseCase {

    private val newsList = listOf<NewsEntity>(
        NewsEntity(
            1,
            "Michael L Hicks ",
            "The Microsoft-Activision acquisition targets Google and Meta more than Sony",
            "This deal could help sell more Xbox Series Xs in the short term, but Microsoft has much bigger VR/AR fish to fry. Microsoft claims its recent Activision Blizzard acquisition is about more than just gaming: it will provide building blocks for the metaverse…",
            "https://img.game-news24.com/2022/01/DOWNLOAD-Microsoft-agrees-to-buy-Activision-Blizzard-for-687-Mrd-USD.webp"
        ),
        NewsEntity(
            2,
            "Nicola Davis Science correspondent",
            "Can they fix it? UK project to explore ability and desire to repair tech",
            "Exclusive: Experts aim to find out whether there are hotspots around country where electronic waste is avoidedFrom fancy toys to smartphones, when technology breaks, it often seems simplest to ditch it for a new model.But now experts are hoping to challenge t…",
            "https://i.guim.co.uk/img/media/4af13fbee58e29226a7b128b201fe72c7fe5c2ae/0_254_5760_3456/master/5760.jpg?width=1200&height=630&quality=85&auto=format&fit=crop&overlay-align=bottom%2Cleft&overlay-width=100p&overlay-base64=L2ltZy9zdGF0aWMvb3ZlcmxheXMvdGctZGVmYXVsdC5wbmc&enable=upscale&s=ff100d9185a80cbe5d3be5729293ce24"
        ),
        NewsEntity(
            3,
            "Editor David",
            "Blender 3.0 Released With More New Features and Improvements",
            "Long-time Slashdot reader Qbertino writes: The Free Open Source 3D production software Blender has been released in version 3.0 (official showreel) with more new features, improvements and performance optimizations as well as further improved workflows. \n\nIn …",
            "https://i0.wp.com/news.itsfoss.com/wp-content/uploads/2021/12/blender-3.png"
        ), NewsEntity(
            4,
            "Aglaia Berlutti",
            "La tercera temporada de ‘Servant’ revela finalmente los grandes misterios de la serie",
            "Durante dos temporadas, la serie Servant de Apple+ creada por M. Night Shyamalan ha sido una combinación de elementos dispares. Los primeros capítulos, estrenados en el 2019, analizaron la idea sobre la pérdida, el dolor y el miedo desde un espacio misterioso…",
            "https://i0.wp.com/hipertextual.com/wp-content/uploads/2019/10/hipertextual-primer-teaser-trailer-servant-serie-m-night-shyamalan-apple-tv-2019757980-scaled.jpg?fit=2560%2C1600&ssl=1"
        )
    )

    fun getAllNews(): List<NewsEntity> {
        return newsList
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
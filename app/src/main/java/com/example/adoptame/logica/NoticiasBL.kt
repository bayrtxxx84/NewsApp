package com.example.adoptame.logica

import com.example.adoptame.casosUso.NewsUseCase
import com.example.adoptame.entidades.News
import kotlin.random.Random

class NoticiasBL {

    fun getNewsList(): List<News> {
        return NewsUseCase().getAllNews()
    }

    fun getOneNews(): News {
        val r = (0..3).random()
        return NewsUseCase().getAllNews()[r]
    }

}
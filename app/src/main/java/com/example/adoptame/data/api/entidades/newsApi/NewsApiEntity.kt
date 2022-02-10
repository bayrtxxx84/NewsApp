package com.example.adoptame.data.api.entidades.newsApi

data class NewsApiEntity(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)
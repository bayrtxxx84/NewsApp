package com.example.adoptame.data.api.entidades

data class ArticlesEntity(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)

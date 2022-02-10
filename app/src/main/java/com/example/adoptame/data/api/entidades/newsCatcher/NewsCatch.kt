package com.example.adoptame.data.api.entidades.newsCatcher

data class NewsCatch(
    val articles: List<Article>,
    val page: Int,
    val page_size: Int,
    val status: String,
    val total_hits: Int,
    val total_pages: Int
)
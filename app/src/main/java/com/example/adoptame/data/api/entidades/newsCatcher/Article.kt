package com.example.adoptame.data.api.entidades.newsCatcher

import com.example.adoptame.database.entidades.NewsEntity

data class Article(
    val _id: String,
    val _score: Double,
    val author: String?,
    val authors: List<Any>,
    val clean_url: String,
    val country: String,
    val excerpt: String,
    val is_opinion: Boolean,
    val language: String,
    val link: String,
    val media: String,
    val published_date: String,
    val published_date_precision: String,
    val rank: Int,
    val rights: String,
    val summary: String,
    val title: String,
    val topic: String,
    val twitter_account: Any
)

fun Article.toNewsEntity() = NewsEntity(0, author, title, summary, media, link)
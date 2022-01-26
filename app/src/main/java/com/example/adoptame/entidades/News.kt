package com.example.adoptame.entidades

import kotlinx.serialization.Serializable

@Serializable
data class News(val author: String, val title: String, val desc: String, val img: String)
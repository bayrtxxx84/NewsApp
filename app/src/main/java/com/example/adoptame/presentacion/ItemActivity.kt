package com.example.adoptame.presentacion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.adoptame.R
import com.example.adoptame.controladores.NoticiasController
import com.example.adoptame.databinding.ActivityItemBinding
import com.example.adoptame.databinding.NewsItemsBinding
import com.example.adoptame.entidades.News
import com.squareup.picasso.Picasso
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class ItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var n: News? = null
        intent.extras?.let {
            n = Json.decodeFromString<News>(it.getString("noticia").toString())
        }
        if (n != null) {
            loadNews(n!!)
        }
    }

    fun loadNews(news: News) {
        binding.txtTitulo.text = news.title
        binding.txtAutor.text = news.author
        binding.txtDesc.text = news.desc
        Picasso.get().load(news.img).into(binding.imgNews)
    }

}
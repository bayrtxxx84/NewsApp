package com.example.adoptame.presentacion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.adoptame.R
import com.example.adoptame.controladores.NewsController
import com.example.adoptame.databinding.ActivityItemBinding
import com.example.adoptame.database.entidades.NewsEntity
import com.example.adoptame.logica.NewsBL
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class ItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemBinding

    private var fav: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var n: NewsEntity? = null
        intent.extras?.let {
            n = Json.decodeFromString<NewsEntity>(it.getString("noticia").toString())
        }
        if (n != null) {
            loadNews(n!!)
        }

        binding.floatingActionButtonItem.setOnClickListener() {
            saveFavNews(n)
        }
    }


    private fun loadNews(newsEntity: NewsEntity) {
        binding.txtTitulo.text = newsEntity.title
        binding.txtAutor.text = newsEntity.author
        binding.txtDesc.text = newsEntity.desc
        Picasso.get().load(newsEntity.img).into(binding.imgNews)

        lifecycleScope.launch(Dispatchers.Main) {
            fav = withContext(Dispatchers.IO) { NewsBL().checkIsSaved(newsEntity.id) }
            if (fav) {
                binding.floatingActionButtonItem.setImageResource(R.drawable.ic_favorite_24)
            }
        }
    }

    private fun saveFavNews(news: NewsEntity?) {
        if (news != null) {
            if (!fav) {
                lifecycleScope.launch {
                    NewsController().saveFavNews(news)
                    binding.floatingActionButtonItem.setImageResource(R.drawable.ic_favorite_24)
                }
            } else {
                lifecycleScope.launch {
                    NewsController().deleteFavNews(news)
                    binding.floatingActionButtonItem.setImageResource(R.drawable.ic_favorite_border_12)
                }
            }
        }
    }
}
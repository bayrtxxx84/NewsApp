package com.example.adoptame.controladores

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.example.adoptame.R
import com.example.adoptame.databinding.NewsItemsBinding
import com.example.adoptame.entidades.News
import com.example.adoptame.presentacion.LoginActivity
import com.squareup.picasso.Picasso

class ListNewsAdapter(val listNews: List<News>, val onClickItemSelected: (News) -> Unit) :
    RecyclerView.Adapter<NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NewsViewHolder(inflater.inflate(R.layout.news_items, parent, false))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = listNews[position]
        holder.render(item, onClickItemSelected)
    }

    override fun getItemCount(): Int = listNews.size
}

class NewsViewHolder(itemNews: View) : RecyclerView.ViewHolder(itemNews) {

    private val binding: NewsItemsBinding = NewsItemsBinding.bind(itemNews)

    fun render(itemNews: News, onClickItemSelected: (News) -> Unit) {
        binding.txtTitleNews.text = itemNews.title
        binding.txtDescNews.text = itemNews.desc
        Picasso.get().load(itemNews.img).into(binding.imageView)

        itemView.setOnClickListener {
            onClickItemSelected(itemNews)
        }
    }
}

package com.example.adoptame.controladores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adoptame.R
import com.example.adoptame.databinding.NewsItemsBinding
import com.example.adoptame.database.entidades.NewsEntity
import com.squareup.picasso.Picasso

class ListNewsAdapter(val listNews: List<NewsEntity>, val onClickItemSelected: (NewsEntity) -> Unit) :
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

    fun render(itemNewsEntity: NewsEntity, onClickItemSelected: (NewsEntity) -> Unit) {
        binding.txtTitleNews.text = itemNewsEntity.title
        Picasso.get().load(itemNewsEntity.img).into(binding.imageView)

        itemView.setOnClickListener {
            onClickItemSelected(itemNewsEntity)
        }
    }
}

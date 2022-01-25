package com.example.adoptame.controladores.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adoptame.R
import com.example.adoptame.databinding.ItemNewsListBinding
import com.example.adoptame.entidades.News
import com.squareup.picasso.Picasso


class NewsAdapter(private val newsItemsList: List<News>, private val onClickListener: (News) -> Unit) :
    RecyclerView.Adapter<NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_news_list, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = newsItemsList[position]
        holder.render(item, onClickListener)
    }

    override fun getItemCount(): Int = newsItemsList.size
}

class NewsViewHolder(newsView: View) : RecyclerView.ViewHolder(newsView) {

    val binding = ItemNewsListBinding.bind(newsView)

    fun render(item: News, onClickListener: (News) -> Unit) {
        binding.txtTitulo.text = item.title
        binding.txtDescripcion.text = item.desc
        Picasso.get().load(item.img).into(binding.imgNews1)

        itemView.setOnClickListener { onClickListener(item) }
    }

}

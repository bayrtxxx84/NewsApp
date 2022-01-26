package com.example.adoptame.presentacion

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adoptame.controladores.ListNewsAdapter
import com.example.adoptame.databinding.FragmentListarBinding
import com.example.adoptame.entidades.News
import com.example.adoptame.logica.NoticiasBL
import com.squareup.picasso.Picasso
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class ListarFragment : Fragment() {

    private lateinit var binding: FragmentListarBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListarBinding.inflate(inflater, container, false)

        loadListNews(NoticiasBL().getNewsList())

        return binding.root

    }

    fun loadListNews(items: List<News>) {
        binding.listRecyclerView.layoutManager =
            LinearLayoutManager(binding.listRecyclerView.context)
        binding.listRecyclerView.adapter = ListNewsAdapter(items) { getNewsItem(it) }
    }

    fun getNewsItem(news: News) {
        var i = Intent(activity, ItemActivity::class.java)
        val jsonString = Json.encodeToString(news)
        i.putExtra("noticia", jsonString)
        startActivity(i)
    }
}
package com.example.adoptame.presentacion

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adoptame.controladores.ListNewsAdapter
import com.example.adoptame.databinding.FragmentListarBinding
import com.example.adoptame.database.entidades.NewsEntity
import com.example.adoptame.logica.NewsBL
import kotlinx.coroutines.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class ListarFragment : Fragment() {

    private lateinit var binding: FragmentListarBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentListarBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onStart() {
        super.onStart()
        binding.progressBar.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Main) {
            val items = withContext(Dispatchers.IO) {
                NewsBL().getNewsList()
            }
            binding.progressBar.visibility = View.INVISIBLE
            loadListNews(items)
        }
    }

    private fun loadListNews(newsEntities: List<NewsEntity>) {
        binding.listRecyclerView.layoutManager =
            LinearLayoutManager(binding.listRecyclerView.context)
        binding.listRecyclerView.adapter = ListNewsAdapter(newsEntities) { getNewsItem(it) }
    }

    private fun getNewsItem(newsEntity: NewsEntity) {
        var i = Intent(activity, ItemActivity::class.java)
        val jsonString = Json.encodeToString(newsEntity)
        i.putExtra("noticia", jsonString)
        startActivity(i)
    }
}
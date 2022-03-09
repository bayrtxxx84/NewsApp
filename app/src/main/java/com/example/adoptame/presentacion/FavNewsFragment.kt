package com.example.adoptame.presentacion

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenResumed
import androidx.lifecycle.whenStarted
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adoptame.controladores.ListNewsAdapter
import com.example.adoptame.controladores.NewsController
import com.example.adoptame.database.entidades.NewsEntity
import com.example.adoptame.databinding.FragmentFavNewsBinding
import com.example.adoptame.logica.NewsBL
import kotlinx.coroutines.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class FavNewsFragment : Fragment() {

    private val newControllerModel: NewsController by viewModels()
    private lateinit var binding: FragmentFavNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        newControllerModel.searchFavNews("")

        binding.floatingActionButton.setOnClickListener {
            if (binding.searchView.visibility == View.GONE) {
                binding.searchView.visibility = View.VISIBLE
            } else {
                binding.searchView.visibility = View.GONE
            }
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                newControllerModel.searchFavNews(query!!)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = true
        })

        binding.searchView.setOnCloseListener(object : SearchView.OnCloseListener,
            androidx.appcompat.widget.SearchView.OnCloseListener {
            override fun onClose(): Boolean {
                newControllerModel.searchFavNews("")
                binding.searchView.visibility = View.GONE
                return true
            }
        })

        newControllerModel.retFavNews.observe(viewLifecycleOwner) {
            binding.progressBarFav.visibility = View.VISIBLE
            lifecycleScope.launch(Dispatchers.Main) {
                binding.listRecyclerViewFav.adapter =
                    ListNewsAdapter(it) { getNewsItem(it) }
                binding.listRecyclerViewFav.layoutManager =
                    LinearLayoutManager(binding.listRecyclerViewFav.context)
                binding.progressBarFav.visibility = View.GONE
            }
        }
    }

    private fun getNewsItem(newsEntity: NewsEntity) {
        var i = Intent(activity, ItemActivity::class.java)
        val jsonString = Json.encodeToString(newsEntity)
        i.putExtra("noticia", jsonString)
        startActivity(i)
    }

}
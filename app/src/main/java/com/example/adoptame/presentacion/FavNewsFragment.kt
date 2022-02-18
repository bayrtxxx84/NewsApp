package com.example.adoptame.presentacion

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adoptame.controladores.ListNewsAdapter
import com.example.adoptame.database.entidades.NewsEntity
import com.example.adoptame.databinding.FragmentFavNewsBinding
import com.example.adoptame.logica.NewsBL
import kotlinx.coroutines.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class FavNewsFragment : Fragment() {

    private lateinit var binding: FragmentFavNewsBinding
    private var job: Job? = null
    private var items = ArrayList<NewsEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()) {
                    var itemsFiltered = items.filter {
                        it.title.toString().contains(query)
                    }
                    binding.listRecyclerViewFav.adapter =
                        ListNewsAdapter(itemsFiltered) { getNewsItem(it) }
                    binding.listRecyclerViewFav.layoutManager =
                        LinearLayoutManager(binding.listRecyclerViewFav.context)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean = true

        })

        binding.searchView.setOnCloseListener {
            onStart()
            binding.searchView.visibility = View.GONE
            false
        }

        binding.floatingActionButton.setOnClickListener() {
            val visible = binding.searchView.visibility

            if (visible == View.GONE) {
                binding.searchView.visibility = View.VISIBLE
                binding.searchView.setQuery("", true);
                binding.searchView.isFocusable = true;
                binding.searchView.isIconified = false;
                binding.searchView.requestFocusFromTouch();
            }
        }

    }

    override fun onStart() {
        super.onStart()
        binding.progressBarFav.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Main) {
            items = withContext(Dispatchers.IO) {
                NewsBL().getFavoritesNews()
            } as ArrayList<NewsEntity>
            binding.listRecyclerViewFav.adapter =
                ListNewsAdapter(items) { getNewsItem(it) }
            binding.listRecyclerViewFav.layoutManager =
                LinearLayoutManager(binding.listRecyclerViewFav.context)
            binding.progressBarFav.visibility = View.INVISIBLE
        }
    }

    private fun getNewsItem(newsEntity: NewsEntity) {
        var i = Intent(activity, ItemActivity::class.java)
        val jsonString = Json.encodeToString(newsEntity)
        i.putExtra("noticia", jsonString)
        startActivity(i)
    }

}
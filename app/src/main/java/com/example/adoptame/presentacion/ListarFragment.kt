package com.example.adoptame.presentacion

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.ViewModelStore
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adoptame.R
import com.example.adoptame.controladores.ListNewsAdapter
import com.example.adoptame.controladores.NewsController
import com.example.adoptame.database.entidades.NewsEntity
import com.example.adoptame.databinding.FragmentListarBinding
import com.example.adoptame.utils.Adoptame
import com.example.adoptame.utils.EnumNews
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class ListarFragment : Fragment() {

    private lateinit var binding: FragmentListarBinding
    private var category: String = "business"
    private var page: Int = 1

    private val newsViewModel: NewsController by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentListarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadNews(category, 1)

        binding.tabLayout.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    val idCat = tab?.position!!
                    category = EnumNews.CategoryNews.fromPosition(idCat)
                    clear()
                    loadNews(category, 1)
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {}
                override fun onTabUnselected(tab: TabLayout.Tab?) {}
            }
        )

        binding.listRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    page += 1
                    loadNews(category, page)
                }
            }
        })

        binding.swipeRefresh.setOnRefreshListener {
            loadNews(category, page)
            binding.swipeRefresh.isRefreshing = false
        }

        newsViewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
        }


        newsViewModel.retNews.observe(viewLifecycleOwner, Observer {
            binding.listRecyclerView.layoutManager =
                LinearLayoutManager(binding.listRecyclerView.context)
            binding.listRecyclerView.adapter = ListNewsAdapter(it) { getNewsItem(it) }
        })
    }

    fun loadNews(category: String, page: Int) {
        binding.listRecyclerView.clearAnimation()
        val db = Adoptame.getPrefseDB()
        println(db.all)
        var keys = ArrayList<Int>()
        val apis = listOf<Int>(
            R.string.newsapi,
            R.string.catchnewsapi
        )
        apis.forEach {
            val check = resources.getResourceEntryName(it)
            //if (db.contains(check)) {
                keys.add(it)
            //}
        }

        if (keys.isEmpty()) {
            Toast.makeText(
                binding.listParentLayout.context,
                "No hay fuentes de información seleccionadas",
                Toast.LENGTH_SHORT
            ).show()
            clear()
        } else {
            newsViewModel.getNews(category, page, keys)
        }
    }

    private fun getNewsItem(newsEntity: NewsEntity) {
        var i = Intent(activity, ItemActivity::class.java)
        val jsonString = Json.encodeToString(newsEntity)
        i.putExtra("noticia", jsonString)
        startActivity(i)
    }

    fun clear() {
        binding.listRecyclerView.adapter = ListNewsAdapter(emptyList()) { }
    }
}
package com.example.adoptame.presentacion

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adoptame.controladores.ListNewsAdapter
import com.example.adoptame.controladores.NewsController
import com.example.adoptame.database.entidades.NewsEntity
import com.example.adoptame.databinding.FragmentListarBinding
import com.example.adoptame.logica.NewsBL
import com.example.adoptame.utils.EnumNews
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class ListarFragment : Fragment() {

    private lateinit var binding: FragmentListarBinding
    private var category: String = EnumNews.SelectionCategory.business.toString()
    private var page: Int = 1

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
                    category = EnumNews.SelectionCategory.fromPosition(idCat)
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
    }

    fun loadNews(category: String, page: Int) {
        binding.listRecyclerView.clearAnimation()
        binding.progressBar.visibility = View.VISIBLE

        lifecycleScope.launch(Dispatchers.Main)
        {
            val items = withContext(Dispatchers.IO) {
                NewsController().getNews(
                    category,
                    page,
                    EnumNews.APITypes.fromName("catcherapi")
                )
            }

            binding.listRecyclerView.layoutManager =
                LinearLayoutManager(binding.listRecyclerView.context)
            binding.listRecyclerView.adapter = ListNewsAdapter(items) { getNewsItem(it) }
            binding.progressBar.visibility = View.GONE
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
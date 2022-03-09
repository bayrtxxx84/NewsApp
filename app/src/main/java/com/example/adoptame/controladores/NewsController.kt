package com.example.adoptame.controladores

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adoptame.R
import com.example.adoptame.database.entidades.NewsEntity
import com.example.adoptame.logica.NewsBL
import com.example.adoptame.utils.Adoptame
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NewsController : ViewModel() {

    val retFavNews = MutableLiveData<List<NewsEntity>>()
    val retNews = MutableLiveData<List<NewsEntity>>()
    val isLoading = MutableLiveData<Boolean>()

    fun searchFavNews(query: String) {
        viewModelScope.launch {
            isLoading.postValue(true)
            var items: ArrayList<NewsEntity> = ArrayList()
            var items1: ArrayList<NewsEntity> = ArrayList()
            items = NewsBL().getFavoritesNews() as ArrayList<NewsEntity>
            if (!query.isNullOrBlank()) {
                items.forEach {
                    if (it.title?.lowercase()?.contains(query.lowercase()) == true) {
                        items1.add(it)
                    }
                }
                retFavNews.postValue(items1)
            } else {
                retFavNews.postValue(items)
            }
            isLoading.postValue(false)
        }
    }

    fun getNews(
        category: String,
        page: Int,
        apiType: List<Int>
    ) {
        viewModelScope.launch {
            isLoading.postValue(true)
            var ret: ArrayList<NewsEntity> = ArrayList<NewsEntity>()
            apiType.forEach {
                Log.d("TAG", it.toString())
                when (it) {
                    R.string.newsapi -> {
                        ret.addAll(NewsBL().getNewsList(category.toString(), page))
                    }
                    R.string.catchnewsapi -> {
                        ret.addAll(NewsBL().getNewsCatchList(category.toString(), page))
                    }
                    else -> {
                        listOf<NewsEntity>()
                    }
                }
            }
            retNews.postValue(ret)
            isLoading.postValue(false)
        }
    }

    suspend fun saveFavNews(news: NewsEntity) {
        NewsBL().saveNewFavNews(news)
    }

    suspend fun deleteFavNews(news: NewsEntity) {
        NewsBL().deleteNewFavNews(news)
    }

}
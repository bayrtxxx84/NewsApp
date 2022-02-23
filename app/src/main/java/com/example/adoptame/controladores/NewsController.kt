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

    val retNews = MutableLiveData<List<NewsEntity>>()
    val isLoading = MutableLiveData<Boolean>()

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
            Log.d("TAG", "Tamaño:  ${ret.size}")
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
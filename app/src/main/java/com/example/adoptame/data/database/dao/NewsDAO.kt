package com.example.adoptame.database.dao

import androidx.room.*
import com.example.adoptame.database.entidades.NewsEntity

@Dao
interface NewsDAO {

    @Query("SELECT * FROM news")
    suspend fun getAllNews(): List<NewsEntity>

    @Query("SELECT * FROM news WHERE id = :idNews")
    suspend fun getNewsById(idNews: Int): NewsEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news: NewsEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNews(news: NewsEntity)

    @Delete()
    suspend fun deleteOneNews(news: NewsEntity)

    @Query("DELETE FROM news")
    suspend fun cleanDbNews()

    @Query("DELETE FROM news WHERE id = :idNews")
    suspend fun deleteNewsById(idNews: Int)

}
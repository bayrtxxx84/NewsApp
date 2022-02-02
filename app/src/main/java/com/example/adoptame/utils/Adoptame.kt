package com.example.adoptame.utils

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.adoptame.database.NewsDataBase

class Adoptame : Application() {

    companion object {
        private var db: NewsDataBase? = null

        fun getDatabase(): NewsDataBase {
            return db!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(applicationContext, NewsDataBase::class.java, "news_DB")
            .build()
    }
}
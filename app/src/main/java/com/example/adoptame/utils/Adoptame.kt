package com.example.adoptame.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.adoptame.database.NewsDataBase

class Adoptame : Application() {

    companion object {
        private var db: NewsDataBase? = null
        private lateinit var dbShare: SharedPreferences

        fun getDatabase(): NewsDataBase {
            return db!!
        }

        fun getShareDB(): SharedPreferences {
            return dbShare!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(applicationContext, NewsDataBase::class.java, "news_DB")
            .build()

        dbShare = applicationContext.getSharedPreferences("login_data", Context.MODE_PRIVATE)
    }
}
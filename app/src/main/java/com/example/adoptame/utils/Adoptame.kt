package com.example.adoptame.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.adoptame.database.NewsDataBase

class Adoptame : Application() {

    companion object {
        private var db: NewsDataBase? = null
        private lateinit var dbShare: SharedPreferences
        private lateinit var dbPreferences : SharedPreferences

        fun getDatabase(): NewsDataBase {
            return db!!
        }

        fun getShareDB(): SharedPreferences {
            return dbShare
        }

        fun getPrefsDB(): SharedPreferences {
            return dbPreferences
        }

    }

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(applicationContext, NewsDataBase::class.java, "news_DB")
            .build()

        dbShare = applicationContext.getSharedPreferences("prefsData", Context.MODE_PRIVATE)
        dbPreferences = PreferenceManager.getDefaultSharedPreferences(this)
    }
}
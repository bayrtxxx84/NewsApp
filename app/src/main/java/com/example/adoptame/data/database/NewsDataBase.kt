package com.example.adoptame.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.adoptame.database.dao.NewsDAO
import com.example.adoptame.database.entidades.NewsEntity

@Database(
    entities = [NewsEntity::class],
    version = 1
)
abstract class NewsDataBase : RoomDatabase() {

    abstract fun newsDao(): NewsDAO

}
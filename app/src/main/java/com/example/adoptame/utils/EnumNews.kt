package com.example.adoptame.utils

class EnumNews {

    enum class CategoryNews {
        business, entertainment, general, health, science, technology, sports;

        companion object {
            fun fromPosition(pos: Int) = CategoryNews.values().firstOrNull {
                it.ordinal == pos
            }.toString()
        }

    }

}
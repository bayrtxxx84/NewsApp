package com.example.adoptame.utils

class EnumNews {

    enum class APITypes(val nameApi: String) {
        NewsApi("newsapi"), NewsCatcherApi("catcherapi");

        companion object {
            fun fromName(name: String) = APITypes.values().first() { it.nameApi == name }
        }
    }

    enum class SelectionCategory {
        business, entertainment, general, health, science, sports, technology;

        companion object {
            fun fromPosition(pos: Int) = SelectionCategory.values().firstOrNull {
                it.ordinal == pos
            }.toString()
        }

    }
}
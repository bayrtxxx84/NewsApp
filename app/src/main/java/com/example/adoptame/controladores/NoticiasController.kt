package com.example.adoptame.controladores

import com.example.adoptame.entidades.News
import com.example.adoptame.logica.NoticiasBL

class NoticiasController {

    fun getOneNews(): News {
        return NoticiasBL().getOneNews()
    }
}
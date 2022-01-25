package com.example.adoptame.controladores

import com.example.adoptame.logica.UsuarioBL

class UsuarioController {

    fun LoginUser(name: String, pass: String): Boolean {
        return UsuarioBL().LoginUser(
            name.trim().uppercase(),
            pass.trim().lowercase()
        )
    }

}
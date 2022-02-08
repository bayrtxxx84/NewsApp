package com.example.adoptame.database.entidades

import java.util.*

data class Usuario(var id: String = "-1L", var nombre: String = "", var password: String = "") {

    constructor (nombre: String, password: String) : this() {
        this.nombre = nombre
        this.password = password
        this.id = UUID.randomUUID().toString()
    }
}
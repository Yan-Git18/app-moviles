package com.example.app_moviles.entities

class Futbolista(var id: String, val nombre: String, val nacionalidad: String, val equipo: String) {

    constructor(): this("", "", "", "")
}
package com.example.app_moviles.entities

class Contact(var id: String, val name: String, val phone: String) {

    constructor(): this("", "", "")
}
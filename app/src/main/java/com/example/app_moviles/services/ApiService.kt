package com.example.app_moviles.services

import com.example.app_moviles.entities.Contact
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    // GET https://68b5a32ae5dc090291afbd0d.mockapi.io/contacts
    @GET("/contacts")
    suspend fun getContacts(): List<Contact>

    // https://68b5a32ae5dc090291afbd0d.mockapi.io/contacts/4
    @GET("/contacts/{id}")
    fun findContactById(@Path("id") id: Int): Contact

}
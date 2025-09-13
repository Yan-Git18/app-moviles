package com.example.app_moviles.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.app_moviles.entities.Contact
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlin.jvm.java

class ContactListViewModel: ViewModel() {
    val contacts = mutableStateListOf<Contact>()
    var isLoading by mutableStateOf(false)
    private val database = Firebase.database
    private val myRef = database.getReference("contacts")

    fun loadContacts() {
        isLoading = true
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (item in snapshot.children) {
                    val contact = item.getValue(Contact::class.java)
                    contacts.add(contact!!)
                }
                isLoading = false
                Log.d("Firebase", contacts.size.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                // aca llega el error de firebase
                isLoading = false
            }

        })
    }
}
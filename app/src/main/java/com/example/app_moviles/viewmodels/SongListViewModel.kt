package com.example.app_moviles.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.app_moviles.entities.Song
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlin.jvm.java

class SongListViewModel: ViewModel() {
    val songs = mutableStateListOf<Song>()
    var isLoading by mutableStateOf(false)
    private val database = Firebase.database
    private val myRef = database.getReference("songs")

    fun loadSongs() {
        isLoading = true
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (item in snapshot.children) {
                    val song = item.getValue(Song::class.java)
                    songs.add(song!!)
                }
                isLoading = false
                Log.d("Firebase", songs.size.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                // aca llega el error de firebase
                isLoading = false
            }

        })
    }
}
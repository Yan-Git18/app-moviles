package com.example.app_moviles

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_moviles.adapters.ContactAdapter
import com.example.app_moviles.entities.Contact
import com.example.app_moviles.entities.Song
import com.google.firebase.Firebase
import com.google.firebase.database.database

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val songs = listOf(
            Song("", "Ed Sheeran", "Perfect"),
            Song("", "Coldplay", "The Scientist"),
            Song("", "Dua Lipa", "Levitating"),
            Song("", "The Weknd", "Blinding Lights"),
            Song("", "Billie Eilish", "bad guy"),
            Song("", "Harry Styles", "As It Was"),
            Song("", "Olivia Rodrigo", "drivers license"),
            Song("", "Ed Sheeran", "Shape of You"),
            Song("", "Taylor Swift", "Anti-Hero"),
            Song("", "Enrique Iglesias", "Heroe")
        )

        insertSongsToFirebase(songs)
    }

    private fun insertSongsToFirebase(songs: List<Song>) {
        val database = Firebase.database
        val myRef = database.getReference("songs")

        songs.forEach { song ->

            val newSongRef = myRef.push()

            val songWithId = Song(
                id = newSongRef.key ?: "",
                autor = song.autor,
                cancion = song.cancion
            )

            newSongRef.setValue(songWithId)
                .addOnSuccessListener {
                    println("Canción '${song.cancion}' de ${song.autor} insertada con ID: ${newSongRef.key}")
                }
                .addOnFailureListener { exception ->
                    println("Error al insertar canción '${song.cancion}': ${exception.message}")
                }
        }
    }
}
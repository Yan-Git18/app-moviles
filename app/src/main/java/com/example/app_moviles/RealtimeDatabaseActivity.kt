package com.example.app_moviles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import  com.example.app_moviles.viewmodels.ContactListViewModel
import com.example.app_moviles.ui.theme.AppmovilesTheme
import com.example.app_moviles.viewmodels.SongListViewModel
import kotlin.getValue

class RealtimeDatabaseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        /*
        setContent {
            AppmovilesTheme {
                val viewmodel: ContactListViewModel by viewModels()

                LaunchedEffect(Unit) {
                    viewmodel.loadContacts()
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center

                    ) {
                        if (viewmodel.isLoading) {
                            CircularProgressIndicator()
                        } else {
                            LazyColumn {
                                items(viewmodel.contacts) { contact ->
                                    Text(text = contact.name)
                                }
                            }
                        }
                    }
                }
            }
        }*/


        setContent {
            AppmovilesTheme {
                val viewmodel: SongListViewModel by viewModels()

                LaunchedEffect(Unit) {
                    viewmodel.loadSongs()
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center

                    ) {
                        if (viewmodel.isLoading) {
                            CircularProgressIndicator()
                        } else {
                            LazyColumn {
                                items(viewmodel.songs) { song ->
                                    Text(text = "${song.cancion} - ${song.autor}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


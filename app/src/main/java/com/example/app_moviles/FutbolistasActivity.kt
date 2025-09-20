package com.example.app_moviles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app_moviles.ui.theme.AppmovilesTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.app_moviles.viewmodels.FutbolistaViewModel



class FutbolistasActivity : ComponentActivity() {

    private val futbolistaViewModel: FutbolistaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppmovilesTheme {
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "lista",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("lista") {
                            ListaFutbolistasScreen(
                                viewModel = futbolistaViewModel,
                                onAddClick = { navController.navigate("registrar") }
                            )
                        }
                        composable("registrar") {
                            RegistrarFutbolista(
                                viewModel = futbolistaViewModel,
                                onRegistered = { navController.popBackStack() } // volver a lista
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ListaFutbolistasScreen(
    viewModel: FutbolistaViewModel,
    onAddClick: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.obtenerFutbolistas()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            items(viewModel.futbolistas) { futbolista ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Nombre: ${futbolista.nombre}")
                        Text("Nacionalidad: ${futbolista.nacionalidad}")
                        Text("Equipo: ${futbolista.equipo}")
                    }
                }
            }
        }
    }
}


@Composable
fun RegistrarFutbolista(
    viewModel: FutbolistaViewModel,
    onRegistered: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var nacionalidad by remember { mutableStateOf("") }
    var equipo by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Column {
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier.padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = nacionalidad,
                onValueChange = { nacionalidad = it },
                label = { Text("Nacionalidad") },
                modifier = Modifier.padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = equipo,
                onValueChange = { equipo = it },
                label = { Text("Equipo") },
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Button(
                onClick = {
                    if (nombre.isNotBlank() && nacionalidad.isNotBlank() && equipo.isNotBlank()) {
                        viewModel.registrarFutbolista(nombre, nacionalidad, equipo)
                        nombre = ""
                        nacionalidad = ""
                        equipo = ""
                        onRegistered() // volver a lista
                    }
                }
            ) {
                Text("Registrar")
            }

            if (viewModel.lastMessage.isNotEmpty()) {
                Text(
                    text = viewModel.lastMessage,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}




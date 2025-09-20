package com.example.app_moviles.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.app_moviles.entities.Futbolista
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class FutbolistaViewModel : ViewModel() {

    private val db = Firebase.firestore

    var futbolistas by mutableStateOf(listOf<Futbolista>())
        private set

    var lastMessage by mutableStateOf("")
        private set

    var isLoading by mutableStateOf(false)
        private set


    fun registrarFutbolista(nombre: String, nacionalidad: String, equipo: String) {
        isLoading = true
        val nuevoFutbolista = hashMapOf(
            "nombre" to nombre,
            "nacionalidad" to nacionalidad,
            "equipo" to equipo
        )

        db.collection("futbolistas")
            .add(nuevoFutbolista)
            .addOnSuccessListener { docRef ->
                lastMessage = "Futbolista $nombre registrado con ID: ${docRef.id}"
                isLoading = false
                Log.d("FutbolistaVM", "Futbolista agregado con ID: ${docRef.id}")
            }
            .addOnFailureListener { e ->
                lastMessage = "Error al registrar futbolista"
                isLoading = false
                Log.e("FutbolistaVM", "Error al guardar", e)
            }
    }


    fun obtenerFutbolistas() {
        isLoading = true
        db.collection("futbolistas")
            .get()
            .addOnSuccessListener { result ->
                val lista = result.map { doc ->
                    Futbolista(
                        id = doc.id, // usamos el ID autogenerado de Firestore
                        nombre = doc.getString("nombre") ?: "",
                        nacionalidad = doc.getString("nacionalidad") ?: "",
                        equipo = doc.getString("equipo") ?: ""
                    )
                }
                futbolistas = lista
                lastMessage = "Futbolistas cargados (${lista.size})"
                isLoading = false
            }
            .addOnFailureListener { e ->
                lastMessage = "Error al obtener futbolistas"
                isLoading = false
                Log.e("FutbolistaVM", "Error al leer", e)
            }
    }

    fun clearMessage() {
        lastMessage = ""
    }
}

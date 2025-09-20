package com.example.app_moviles.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class LoginViewModel : ViewModel() {
    var userId by mutableStateOf("")
    var isValidAuth by mutableStateOf(false)
    var isLoading by mutableStateOf(false)
    var authMessage by mutableStateOf("")
    private val auth = Firebase.auth

    fun login(email: String, password: String) {
        isLoading = true
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                authMessage = ""
                isLoading = false
                isValidAuth = true
                userId = result.user?.uid!!
            }
            .addOnFailureListener { error ->
                Log.e("MAIN_APP", "Error al iniciar sesión", error)
                authMessage = "Usuario y/o contraseña incorrecto"
                isLoading = false
                isValidAuth = false
            }
    }
}
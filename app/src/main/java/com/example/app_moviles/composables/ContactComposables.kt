package com.example.app_moviles.composables


import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.app_moviles.entities.Contact
import com.example.app_moviles.services.ApiService

@Composable
fun ListaContactos() {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://68b5a32ae5dc090291afbd0d.mockapi.io")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService = retrofit.create(ApiService::class.java)


    var isLoading by remember { mutableStateOf(true) }
    var hasNetworkError by remember { mutableStateOf(false) }
    var contacts by remember { mutableStateOf(listOf<Contact>()) }

    LaunchedEffect(Unit) {
        try {
            isLoading = true
            hasNetworkError = false
            contacts = apiService.getContacts()
        } catch (e: Exception) {
            hasNetworkError = true
            Log.e("MAIN_APP", e.toString())
        } finally {
            isLoading = false
        }
    }
    if (hasNetworkError) {
        Text(text = "No se ha podido conectar con el servicio para obtener los contactos")
    } else {
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column {
                Button(
                    onClick = {
                        contacts = contacts + Contact("","Nuevo Contacto", "000000000")
                    }
                ) {
                    Text(text = "Add Random Contact")
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                )
                {
                    items(contacts.size) { index ->
                        val contact = contacts[index]
                        Column {
                            Text(
                                text = contact.name,
                            )
                            Text(
                                text = contact.phone
                            )
                        }
                        HorizontalDivider(
                            Modifier,
                            DividerDefaults.Thickness,
                            DividerDefaults.color
                        )
                    }
                }
            }
        }
    }
}
package com.example.app_moviles

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import com.example.app_moviles.entities.Contact
import com.example.app_moviles.ui.theme.AppmovilesTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlin.getValue
import com.example.app_moviles.viewmodels.LoginViewModel

class LoginActivity : ComponentActivity() {

    fun changeActivity(context: Context) {
        val intent = Intent(context, RealtimeDatabaseActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            AppmovilesTheme {
                val preferences = getSharedPreferences("com.example.app_moviles", MODE_PRIVATE)
                val estaLogueado = preferences.getBoolean("ESTA_LOGUEADO", false)
                val viewModel: LoginViewModel by viewModels()
                val context = LocalContext.current

                if (estaLogueado) {
                    changeActivity(context)
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    var email by remember { mutableStateOf("") }
                    var password by remember { mutableStateOf("") }


                    LaunchedEffect(viewModel.isValidAuth) {
                        if (viewModel.isValidAuth) {
                            // guardamos preferencias

                            preferences.edit {
                                putBoolean("ESTA_LOGUEADO", true)
                                putString("USERID", viewModel.userId)
                            }
                            // cambiar de pantalla a otra
                            changeActivity(context)
                        }
                    }

                    Column(
                        modifier = Modifier.padding(innerPadding)
                            .fillMaxSize()
                            .padding(24.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Inicio de sesión", style = MaterialTheme.typography.headlineMedium)
                        if (viewModel.authMessage != "") {
                            Text(viewModel.authMessage)
                        }
                        Spacer(Modifier.height(24.dp))

                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text("Email") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email,
                                imeAction = ImeAction.Next
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(Modifier.height(12.dp))

                        OutlinedTextField(
                            value = password,
                            onValueChange = { it -> password = it },
                            label = { Text("Password") },
                            singleLine = true,
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Done
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(Modifier.height(20.dp))

                        Button(
                            onClick = {
                                viewModel.login(email, password)
                            },
                            enabled = !viewModel.isLoading,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                        ) {
                            Text("Login")
                        }

                    }
                }
            }
        }
    }
}


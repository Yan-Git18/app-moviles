package com.example.app_moviles

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.app_moviles.ui.theme.AppmovilesTheme

class JetPackActivity : ComponentActivity() {
//    var counter = 0;
//    var counter = mutableStateOf(0)
//    var counter by mutableStateOf(0)

    //    var lista by mutableStateOf(listOf<String>())
//        var lista = mutableStateListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppmovilesTheme {

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier.fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        Column {
                            var counter by remember { mutableStateOf(0) }

                            Text(
                                text = "$counter",
                                modifier = Modifier.padding(innerPadding)
                            )
                            Button(
                                onClick = {
                                    counter++
                                    Log.i("MAIN_APP", "Counter: $counter")
                                },
                            ) {
                                Text(text = "Click!")
                            }
                        }

                    }

                }
            }
        }
    }
}

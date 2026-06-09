package com.example.lab13_moviles.exercises

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

enum class ScreenState {
    Loading, Content, Error
}

@Composable
fun Exercise4() {
    var currentState by remember { mutableStateOf(ScreenState.Loading) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Botón para rotar entre estados
        Button(onClick = {
            currentState = when (currentState) {
                ScreenState.Loading -> ScreenState.Content
                ScreenState.Content -> ScreenState.Error
                ScreenState.Error -> ScreenState.Loading
            }
        }) {
            Text(text = "Siguiente Estado")
        }

        Spacer(modifier = Modifier.height(40.dp))

        // AnimatedContent para transiciones suaves entre componentes
        AnimatedContent(
            targetState = currentState,
            transitionSpec = {
                fadeIn(animationSpec = tween(durationMillis = 800)) togetherWith
                        fadeOut(animationSpec = tween(durationMillis = 800))
            },
            label = "StateTransition"
        ) { state ->
            when (state) {
                ScreenState.Loading -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "Cargando datos...", style = MaterialTheme.typography.bodyLarge)
                    }
                }
                ScreenState.Content -> {
                    Text(
                        text = "¡Contenido cargado con éxito!",
                        color = Color(0xFF4CAF50),
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
                ScreenState.Error -> {
                    Text(
                        text = "Error: No se pudo conectar al servidor.",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

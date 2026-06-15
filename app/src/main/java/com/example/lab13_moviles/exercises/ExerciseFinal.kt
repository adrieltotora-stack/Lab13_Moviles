package com.example.lab13_moviles.exercises

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class GameState {
    Start, Battle, Victory
}

@Composable
fun ExerciseFinal() {
    var gameState by remember { mutableStateOf(GameState.Start) }
    var playerHealth by remember { mutableFloatStateOf(1f) }
    var isPoweredUp by remember { mutableStateOf(false) }
    var enemyVisible by remember { mutableStateOf(false) }

    // 1. Animación de Color de Fondo (animateColorAsState)
    val backgroundColor by animateColorAsState(
        targetValue = when (gameState) {
            GameState.Start -> Color(0xFF1B5E20)
            GameState.Battle -> Color(0xFFB71C1C)
            GameState.Victory -> Color(0xFF0D47A1)
        },
        animationSpec = tween(1000),
        label = "BgColor"
    )

    // 2. Animación de Tamaño (animateDpAsState)
    val playerSize by animateDpAsState(
        targetValue = if (isPoweredUp) 120.dp else 80.dp,
        animationSpec = spring(dampingRatio = Spring.DampingRatioHighBouncy),
        label = "PlayerSize"
    )

    // 3. Animación de Posición (animateDpAsState)
    val playerOffset by animateDpAsState(
        targetValue = if (gameState == GameState.Battle) (-60).dp else 0.dp,
        animationSpec = tween(500),
        label = "PlayerPos"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 4. Cambio de Contenido con Transición (AnimatedContent)
        AnimatedContent(
            targetState = gameState,
            label = "TitleTransition"
        ) { state ->
            Text(
                text = when (state) {
                    GameState.Start -> "MUNDO EXPLORACIÓN"
                    GameState.Battle -> "¡JEFE FINAL!"
                    GameState.Victory -> "¡NIVEL COMPLETADO!"
                },
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Barra de Vida con Animación Fluida
        val animatedHealth by animateFloatAsState(
            targetValue = playerHealth,
            animationSpec = tween(500),
            label = "Health"
        )

        LinearProgressIndicator(
            progress = { animatedHealth },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(12.dp)
                .clip(RoundedCornerShape(6.dp)),
            color = if (playerHealth < 0.3f) Color.Red else Color.Cyan
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            // JUGADOR
            Box(
                modifier = Modifier
                    .offset(x = playerOffset)
                    .size(playerSize)
                    .clip(CircleShape)
                    .background(if (isPoweredUp) Color.Yellow else Color.White),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Face, contentDescription = null, modifier = Modifier.size(size = playerSize / 2))
            }

            // ENEMIGO con Animación de Entrada/Salida
            androidx.compose.animation.AnimatedVisibility(
                visible = enemyVisible,
                enter = fadeIn() + expandHorizontally(),
                exit = fadeOut() + shrinkHorizontally(),
                modifier = Modifier.offset(x = 70.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Black, RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Warning, contentDescription = null, tint = Color.Red, modifier = Modifier.size(50.dp))
                }
            }
        }

        // BOTONES DE ACCIÓN
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            when (gameState) {
                GameState.Start -> {
                    Button(onClick = {
                        gameState = GameState.Battle
                        enemyVisible = true
                    }) { Text("Iniciar Batalla") }
                }
                GameState.Battle -> {
                    Button(onClick = { isPoweredUp = !isPoweredUp }) {
                        Text(if (isPoweredUp) "Normal" else "Poder!")
                    }
                    Button(onClick = {
                        playerHealth -= 0.25f
                        if (playerHealth <= 0f) {
                            gameState = GameState.Victory
                            enemyVisible = false
                        }
                    }, colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) {
                        Text("Atacar")
                    }
                }
                GameState.Victory -> {
                    Button(onClick = {
                        gameState = GameState.Start
                        playerHealth = 1f
                        isPoweredUp = false
                    }) { Text("Volver a Jugar") }
                }
            }
        }
    }
}

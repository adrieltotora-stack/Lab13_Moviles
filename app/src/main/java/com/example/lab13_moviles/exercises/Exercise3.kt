package com.example.lab13_moviles.exercises

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Exercise3() {
    var isExpanded by remember { mutableStateOf(false) }

    // Animación de tamaño
    val size by animateDpAsState(
        targetValue = if (isExpanded) 200.dp else 100.dp,
        animationSpec = tween(durationMillis = 500),
        label = "SizeAnimation"
    )

    // Animación de posición (offset)
    val offsetX by animateDpAsState(
        targetValue = if (isExpanded) 50.dp else 0.dp,
        animationSpec = tween(durationMillis = 500),
        label = "PositionAnimationX"
    )
    
    val offsetY by animateDpAsState(
        targetValue = if (isExpanded) 100.dp else 0.dp,
        animationSpec = tween(durationMillis = 500),
        label = "PositionAnimationY"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        
        Button(onClick = { isExpanded = !isExpanded }) {
            Text(text = if (isExpanded) "Restablecer" else "Mover y Crecer")
        }

        Spacer(modifier = Modifier.height(20.dp))

        // El orden de los modificadores es importante
        Box(
            modifier = Modifier
                .offset(x = offsetX, y = offsetY) // Aplicamos primero el movimiento
                .size(size)                      // Luego el cambio de tamaño
                .background(Color.Red)
        )
    }
}

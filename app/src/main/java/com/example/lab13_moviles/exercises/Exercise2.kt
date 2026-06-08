package com.example.lab13_moviles.exercises

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.spring
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
fun Exercise2() {
    var isBlue by remember { mutableStateOf(true) }
    
    // Animación de color con animateColorAsState
    val backgroundColor by animateColorAsState(
        targetValue = if (isBlue) Color.Blue else Color.Green,
        // Experimentando con tween (duración fija) o spring (efecto rebote)
        animationSpec = spring(dampingRatio = 0.5f, stiffness = 100f),
        label = "ColorAnimation"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { isBlue = !isBlue }) {
            Text(text = "Cambiar Color")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .size(150.dp)
                .background(backgroundColor)
        )
        
        Spacer(modifier = Modifier.height(10.dp))
        
        Text(text = "Color actual: ${if (isBlue) "Azul" else "Verde"}")
    }
}

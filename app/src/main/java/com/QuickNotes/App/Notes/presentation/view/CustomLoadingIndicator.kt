package com.QuickNotes.App.Notes.presentation.view

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.unit.dp

@Composable
fun CustomLoadingIndicator() {
    // Анимация прогресса
    val infiniteTransition = rememberInfiniteTransition()
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(16.dp) // Высота индикатора
    ) {
        val cornerRadius = size.height / 2 // Закругленные углы

        // Фон
        drawRoundRect(
            color = Color(0xFFFFF0F0), // Цвет фона индикатора (розоватый)
            size = Size(size.width, size.height),
            cornerRadius = CornerRadius(cornerRadius, cornerRadius)
        )

        // Прогресс
        drawRoundRect(
            color = Color(0xFF8BE9FD), // Цвет прогресса (голубоватый)
            size = Size(size.width * progress, size.height),
            cornerRadius = CornerRadius(cornerRadius, cornerRadius)
        )
    }
}

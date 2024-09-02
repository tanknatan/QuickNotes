package com.QuickNotes.App.Notes.presentation.view


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.QuickNotes.App.Notes.R
import com.QuickNotes.App.Notes.presentation.navigation.OutlinedText
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onTimeout: () -> Unit) {

    LaunchedEffect(Unit) {
        delay(1500) // Короткая задержка перед переходом
        onTimeout()
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Фон изображения
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            contentScale = ContentScale.Crop, // Чтобы изображение заполнило весь экран
            modifier = Modifier.fillMaxSize()
        )

        // Колонка с текстом "Loading..." и индикатором прогресса
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp), // Добавить отступы, если необходимо
            verticalArrangement = Arrangement.Bottom, // Разместить контент внизу
            horizontalAlignment = Alignment.Start // Выравнивание по левому краю
        ) {
            // Текст "Loading..."

            OutlinedText(
                text = "Loading...",
                outlineColor = Color.Gray,
                fillColor = Color.White,
                fontSize = 50.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(bottom = 8.dp) // Отступ от индикатора прогресса
            )

            // Прогресс-бар
            CustomLoadingIndicator()
        }
    }
}


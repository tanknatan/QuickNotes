package com.QuickNotes.App.Notes.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.QuickNotes.App.Notes.R
import com.QuickNotes.App.Notes.presentation.viewModel.NoteViewModel

@Composable
fun NoteDetailScreen(noteId: Int, viewModel: NoteViewModel, onBackClick: () -> Unit) {
    val note = viewModel.notes.collectAsState().value.find { it.id == noteId }

    note?.let {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Фоновое изображение
            Image(
                painter = painterResource(id = R.drawable.background), // Замените на ваш ресурс изображения
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Верхняя панель с кнопкой назад
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp,top = 40.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBackClick) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_back), // Используем ваше изображение кнопки назад
                            contentDescription = "Back",
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Прямоугольник для заголовка
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color(0xFF94DD90), // Цвет фона прямоугольника
                            shape = RoundedCornerShape(50) // Закругленные углы
                        )
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                ) {
                    Text(
                        text = it.title,
                        style = TextStyle(
                            fontSize = 24.sp,
                            color = Color(0xFF445566),
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Прямоугольник для текста с вертикальным скроллом
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f) // Используем вес для равномерного распределения пространства
                        .background(
                            color = Color(0xFFE3E3D7), // Цвет фона для текста
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()) // Добавляем скролл
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFA5D8FF), shape = RoundedCornerShape(30.dp)) // Закругленные углы
                            .padding(15.dp)
                    ) {
                        Text(
                            text = it.content,
                            style = TextStyle(
                                fontSize = 18.sp,
                                color = Color(0xFF2D2D2D),
                                lineHeight = 24.sp
                            )
                        )
                    }
                }
            }
        }
    }
}




package com.QuickNotes.App.Notes.presentation.view

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.QuickNotes.App.Notes.R
import com.QuickNotes.App.Notes.domain.Note
import com.QuickNotes.App.Notes.presentation.navigation.Destinations
import com.QuickNotes.App.Notes.presentation.navigation.OutlinedText
import com.QuickNotes.App.Notes.presentation.viewModel.NoteViewModel
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun NoteAddScreen(viewModel: NoteViewModel, onBackPressed: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var isSaving by remember { mutableStateOf(false) } // Переменная для отслеживания состояния сохранения

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background), // Фоновое изображение
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(80.dp)) // Отступ сверху

            // Поле для ввода заголовка
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFFDEFFDC), Color(0xFFBCDEB0)), // Градиентные цвета
                            start = Offset(0f, 0f),
                            end = Offset(1000f, 1000f) // Настройте углы градиента по необходимости
                        ),
                        shape = RoundedCornerShape(50) // Закругленные углы
                    )
                    .border(
                        width = 2.dp,
                        color = Color(0xFF7B8794), // Цвет границы
                        shape = RoundedCornerShape(50) // Граница с закругленными углами
                    )
                    .padding(vertical = 8.dp, horizontal = 16.dp)
            ) {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent, // Убираем нижнюю линию при фокусе
                        unfocusedIndicatorColor = Color.Transparent, // Убираем нижнюю линию
                        textColor = Color(0xFF445566) // Цвет текста
                    ),
                    textStyle = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Поле для ввода содержания заметки
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f) // Устанавливаем высоту прямоугольника для текста
                    .background(
                        color = Color(0xFFE3E3D7), // Цвет фона для внешнего прямоугольника
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()) // Добавляем скролл
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFA5D8FF), shape = RoundedCornerShape(30.dp)) // Цвет и форма внутреннего прямоугольника
                        .border(
                            width = 2.dp,
                            color = Color(0xFF7B8794), // Цвет границы внутреннего прямоугольника
                            shape = RoundedCornerShape(30.dp)
                        )
                        .padding(8.dp)
                ) {
                    TextField(
                        value = content,
                        onValueChange = { content = it },
                        label = { Text("Text") }, // Устанавливаем метку текста
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Transparent), // Делаем фон прозрачным
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent, // Убираем нижнюю линию при фокусе
                            unfocusedIndicatorColor = Color.Transparent // Убираем нижнюю линию
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Кнопка "Сохранить"
            TextButton(
                onClick = {
                    if (title.isNotEmpty() && content.isNotEmpty() && !isSaving) {
                        isSaving = true // Блокируем повторные нажатия
                        val newNote = Note(
                            title = title,
                            content = content,
                            creationDate = Date()
                        )
                        viewModel.addNote(newNote)
                        onBackPressed() // Вернуться назад после сохранения
                    }
                },
                enabled = !isSaving, // Делаем кнопку неактивной во время сохранения
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .background(Color(0xFFA5D8FF), shape = RoundedCornerShape(20.dp))
            ) {
                OutlinedText(
                    text = "Save",
                    outlineColor = Color.Gray,
                    fillColor = Color.White,
                    fontSize = 25.sp,
                )
            }
        }

        // Кнопка "Удалить" в верхнем правом углу
        IconButton(
            onClick = {
                onBackPressed() // Возвращаемся на предыдущий экран без сохранения
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp, 40.dp, 0.dp, 0.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_delete), // Иконка "Удалить"
                contentDescription = "Delete",
                modifier = Modifier.size(36.dp)
            )
        }

        // Кнопка "Назад" в верхнем левом углу
        IconButton(
            onClick = { onBackPressed() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(8.dp, 40.dp, 0.dp, 0.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_back), // Иконка "Назад"
                contentDescription = "Back",
                modifier = Modifier.size(36.dp)
            )
        }
    }
}


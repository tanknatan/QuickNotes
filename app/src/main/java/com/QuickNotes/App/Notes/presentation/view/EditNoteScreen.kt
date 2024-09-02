package com.QuickNotes.App.Notes.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults

import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.QuickNotes.App.Notes.R
import com.QuickNotes.App.Notes.domain.Note
import com.QuickNotes.App.Notes.presentation.navigation.OutlinedText
import com.QuickNotes.App.Notes.presentation.viewModel.NoteViewModel
@Composable
fun NoteEditScreen(noteId: Int, viewModel: NoteViewModel, onBackClick: () -> Unit) {
    val note = viewModel.notes.collectAsState().value.find { it.id == noteId }
    var title by remember { mutableStateOf(note?.title ?: "") }
    var content by remember { mutableStateOf(note?.content ?: "") }

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
                .padding(16.dp) // Увеличиваем отступ сверху для опускания всех элементов
        ) {
            Spacer(modifier = Modifier.height(80.dp)) // Добавляем отступ для опускания всех элементов


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

            // Прямоугольник для текста с вертикальным скроллом
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f) // Устанавливаем высоту прямоугольника для текста
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
                    TextField(
                        value = content,
                        onValueChange = { content = it },
                        modifier = Modifier.fillMaxSize(),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Кнопка для сохранения изменений
            TextButton(
                onClick = {
                    val updatedNote = note?.copy(title = title, content = content)
                    if (updatedNote != null) {
                        viewModel.updateNote(updatedNote)
                    }
                    onBackClick()
                },
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
                note?.let {
                    viewModel.deleteNote(it)
                }
                onBackClick()
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
            onClick = { onBackClick() },
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



//@Composable
//fun NoteEditScreen(noteId: Int, viewModel: NoteViewModel, onBackClick: () -> Unit) {
//    val note = viewModel.notes.collectAsState().value.find { it.id == noteId }
//    var title by remember { mutableStateOf(note?.title ?: "") }
//    var content by remember { mutableStateOf(note?.content ?: "") }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//
//    ) {
//        Image(
//            painter = painterResource(id = R.drawable.background), // Замените на ваш ресурс изображения
//            contentDescription = null,
//            contentScale = ContentScale.Crop,
//            modifier = Modifier.fillMaxSize()
//        )
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(top = 16.dp)
//        ) {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(
//                        color = Color(0xFF94DD90), // Цвет фона прямоугольника
//                        shape = RoundedCornerShape(50) // Закругленные углы
//                    )
//                    .padding(vertical = 8.dp, horizontal = 16.dp)
//            ) {
//                TextField(
//                    value = title,
//                    onValueChange = { title = it },
//                    label = { Text("Notes name") },
//                    modifier = Modifier.fillMaxWidth(),
//                    colors = TextFieldDefaults.textFieldColors(
//                        backgroundColor = Color.Transparent
//                    )
//                )
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // Прямоугольник для текста с вертикальным скроллом
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .weight(1f) // Используем вес для равномерного распределения пространства
//                    .background(
//                        color = Color(0xFFE3E3D7), // Цвет фона для текста
//                        shape = RoundedCornerShape(16.dp)
//                    )
//                    .padding(16.dp)
//                    .verticalScroll(rememberScrollState()) // Добавляем скролл
//            ) {
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .background(Color(0xFFA5D8FF), shape = RoundedCornerShape(30.dp)) // Закругленные углы
//                        .padding(15.dp)
//                ) {
//                    TextField(
//                        value = content,
//                        onValueChange = { content = it },
//                        label = { Text("Text") },
//                        modifier = Modifier.fillMaxSize(),
//                        colors = TextFieldDefaults.textFieldColors(
//                            backgroundColor = Color.Transparent
//                        )
//                    )
//                }
//            }
//            // Поле для редактирования заголовка
//
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // Кнопка для сохранения изменений
//            TextButton(
//                onClick = {
//                    val updatedNote = note?.copy(title = title, content = content)
//                    if (updatedNote != null) {
//                        viewModel.updateNote(updatedNote)
//                    }
//                    onBackClick()
//                },
//                modifier = Modifier
//                    .align(Alignment.CenterHorizontally)
//                    .background(Color(0xFFA5D8FF), shape = RoundedCornerShape(20.dp))
//            ) {
//                OutlinedText(
//                    text = "Save",
//                    outlineColor = Color.Gray,
//                    fillColor = Color.White,
//                    fontSize = 25.sp,
//
//                )
//            }
//        }
//
//        // Кнопка "Удалить" в верхнем правом углу
//        IconButton(
//            onClick = {
//                note?.let {
//                    viewModel.deleteNote(it)
//                }
//                onBackClick()
//            },
//            modifier = Modifier
//                .align(Alignment.TopEnd)
//                .padding(8.dp)
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.ic_delete), // Используем ваше изображение иконки "удалить"
//                contentDescription = "Delete",
//                modifier = Modifier.size(36.dp)
//            )
//        }
//    }
//}



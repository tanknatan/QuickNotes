package com.QuickNotes.App.Notes.presentation.view

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.QuickNotes.App.Notes.R
import com.QuickNotes.App.Notes.domain.Note
import com.QuickNotes.App.Notes.presentation.navigation.Destinations
import com.QuickNotes.App.Notes.presentation.viewModel.NoteViewModel
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun NotesScreen(viewModel: NoteViewModel, navController: NavController) {
    val notes = viewModel.notes.collectAsState().value
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Фон экрана
        Image(
            painter = painterResource(id = R.drawable.background), // Замените на свой ресурс изображения
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Основной контент
        Column(
            modifier = Modifier
                .padding(top = 30.dp, start = 30.dp, end = 30.dp, bottom = 30.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBar(navController)
            Spacer(modifier = Modifier.height(16.dp)) // Добавляем больше отступа
            SearchBar(viewModel = viewModel)
            Spacer(modifier = Modifier.height(16.dp)) // Добавляем больше отступа
            NotesListWithBackground(notes = notes, onAddNote = {
                coroutineScope.launch {
                    viewModel.addNote(it)
                }
            },viewModel = viewModel,navController = navController)
        }
    }
}
@Composable
fun SearchBar(viewModel: NoteViewModel) {
    // Используем collectAsState напрямую
    val textState = viewModel.searchQuery.collectAsState()

    BasicTextField(
        value = textState.value,
        onValueChange = {
            viewModel.onSearchQueryChanged(it)
        },
        textStyle = TextStyle(color = Color.Black, fontSize = 18.sp),
        modifier = Modifier
            .background(Color(0xFFA5D8FF), shape = RoundedCornerShape(30.dp))
            .fillMaxWidth()
            .padding(12.dp),
        decorationBox = { innerTextField ->
            if (textState.value.isEmpty()) {
                Text(
                    text = "Search notes",
                    style = TextStyle(color = Color(0xFF88C0E8), fontSize = 18.sp)
                )
            }
            innerTextField()
        }
    )
}



@Composable
fun TopBar(navController: NavController) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { navController.navigate(Destinations.SettingsScreen.route) }) {
            Image(
                painter = painterResource(id = R.drawable.ic_settings), // Замените на свой ресурс изображения
                contentDescription = "Tools",
                modifier = Modifier.size(36.dp)
            )
        }
        IconButton(onClick = {
            (context as? Activity)?.finish()
        }) {
            Image(
                painter = painterResource(id = R.drawable.ic_close), // Замените на свой ресурс изображения
                contentDescription = "Exit",
                modifier = Modifier.size(36.dp)
            )
        }
    }
}

@Composable
fun NotesListWithBackground(notes: List<Note>, onAddNote: (Note) -> Unit, viewModel: NoteViewModel,navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(1f)
            .background(Color(0xFFE3E3D7), shape = RoundedCornerShape(30.dp)) // Закругленные углы
            .padding(15.dp)
    ) {
        TopButtons(viewModel = viewModel,navController = navController)

        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .padding(top = 65.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(notes) { note ->
                ToDoItem(note,navController)
            }
        }
    }
}

@Composable
fun TopButtons(viewModel: NoteViewModel, navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextButton(onClick = {
            viewModel.toggleSortByDate() // Переключение сортировки по дате
        }) {
            Image(
                painter = painterResource(id = R.drawable.ic_sort), // Замените на свой ресурс изображения
                contentDescription = "Sort by Date",
                modifier = Modifier.size(24.dp)
            )
        }
        TextButton(onClick = {
            navController.navigate(Destinations.NoteAddScreen.route)
        }) {
            Image(
                painter = painterResource(id = R.drawable.ic_add), // Замените на свой ресурс изображения
                contentDescription = "Add Note",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}



@Composable
fun ToDoItem(note: Note, navController: NavController) {
    var isLongPressed by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF88C0E8), shape = RoundedCornerShape(30.dp))
            .padding(8.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        isLongPressed = true
                        navController.navigate(Destinations.NoteEditScreen.createRoute(note.id))
                    },
                    onTap = {
                        if (!isLongPressed) {
                            navController.navigate(Destinations.NoteDetailScreen.createRoute(note.id))
                        }
                        isLongPressed = false
                    }
                )
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${note.id}: ${note.title}",
            modifier = Modifier.padding(16.dp),
            style = TextStyle(fontSize = 18.sp, color = Color.White)
        )
    }
}


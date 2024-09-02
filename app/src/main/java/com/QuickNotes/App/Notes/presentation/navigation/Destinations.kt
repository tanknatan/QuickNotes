package com.QuickNotes.App.Notes.presentation.navigation

sealed class Destinations(val route: String) {

    data object SplashScreen : Destinations("splash_screen")
   data object NoteScreen : Destinations("main_screen")
    object NoteDetailScreen : Destinations("note_detail/{noteId}") {
        fun createRoute(noteId: Int) = "note_detail/$noteId"
    }
    object NoteEditScreen : Destinations("note_edit/{noteId}") {
        fun createRoute(noteId: Int) = "note_edit/$noteId"
    }
    data object NoteAddScreen : Destinations("add_note_screen")
    data object SettingsScreen : Destinations("settings_screen")

}
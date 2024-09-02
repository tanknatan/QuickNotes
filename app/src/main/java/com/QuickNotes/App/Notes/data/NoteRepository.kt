//package com.QuickNotes.App.Notes.data
//
//import android.content.Context
//import com.QuickNotes.App.Notes.domain.Note
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//
//class NoteRepository(context: Context) {
//
//    private val noteDao = DatabaseProvider.getDatabase(context).noteDao()
//
//    fun insertNote(note: Note) {
//        CoroutineScope(Dispatchers.IO).launch {
//            noteDao.insert(note)
//        }
//    }
//
//    fun updateNote(note: Note) {
//        CoroutineScope(Dispatchers.IO).launch {
//            noteDao.update(note)
//        }
//    }
//
//    fun deleteNote(note: Note) {
//        CoroutineScope(Dispatchers.IO).launch {
//            noteDao.delete(note)
//        }
//    }
//
//    fun getAllNotes(callback: (List<Note>) -> Unit) {
//        CoroutineScope(Dispatchers.IO).launch {
//            val notes = noteDao.getAllNotes()
//            CoroutineScope(Dispatchers.Main).launch {
//                callback(notes)
//            }
//        }
//    }
//}

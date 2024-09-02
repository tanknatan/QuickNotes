package com.QuickNotes.App.Notes.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.QuickNotes.App.Notes.domain.NoteDao

class NoteViewModelFactory(private val dao: NoteDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

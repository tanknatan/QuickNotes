package com.QuickNotes.App.Notes.presentation.viewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.QuickNotes.App.Notes.domain.Note
import com.QuickNotes.App.Notes.domain.NoteDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
class NoteViewModel(private val dao: NoteDao) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery


    private val _isSortedByDate = MutableStateFlow(true)

    val notes: StateFlow<List<Note>> = combine(_searchQuery, _isSortedByDate) { query, isSortedByDate ->
        query to isSortedByDate
    }.flatMapLatest { (query, isSortedByDate) ->
        if (isSortedByDate) {
            if (query.isEmpty()) {
                dao.getAllNotesSortedByDate()
            } else {
                dao.searchNotesSortedByDate("%$query%")
            }
        } else {
            if (query.isEmpty()) {
                dao.getAllNotes()
            } else {
                dao.searchNotes("%$query%")
            }
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun toggleSortByDate() {
        _isSortedByDate.value = !_isSortedByDate.value
    }

    fun addNote(note: Note) {
        viewModelScope.launch {
            dao.insert(note)
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            dao.update(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            dao.delete(note)
        }
    }
}


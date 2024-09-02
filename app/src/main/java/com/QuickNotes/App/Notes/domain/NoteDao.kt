package com.QuickNotes.App.Notes.domain
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

import androidx.room.*

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM notes ORDER BY creationDate DESC")
    fun getAllNotesSortedByDate(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE title LIKE :query ORDER BY creationDate DESC")
    fun searchNotesSortedByDate(query: String): Flow<List<Note>>

    @Query("SELECT * FROM notes ORDER BY title ASC")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE title LIKE :query ORDER BY title ASC")
    fun searchNotes(query: String): Flow<List<Note>>
}






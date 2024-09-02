package com.QuickNotes.App.Notes.domain

import androidx.room.Entity
import androidx.room.PrimaryKey


import java.util.Date

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val content: String,
    val creationDate: Date
)


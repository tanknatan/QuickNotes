package com.QuickNotes.App.Notes.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.QuickNotes.App.Notes.domain.Note
import com.QuickNotes.App.Notes.domain.NoteDao

import android.content.Context
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance


import androidx.room.Room
import androidx.room.TypeConverters


@Database(entities = [Note::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "note_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

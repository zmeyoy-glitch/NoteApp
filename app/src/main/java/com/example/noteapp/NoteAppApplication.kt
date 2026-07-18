package com.example.noteapp

import android.app.Application
import androidx.room.Room

class NoteAppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        
        // Initialize Room database with singleton pattern
        val database = Room.databaseBuilder(
            this,
            NoteDatabase::class.java,
            "note_database"
        ).build()
        
        // Optional: Add database instance to application context for easy access
        // This can be useful if you need to access the DB from other components
    }

    fun getNoteDatabase(): NoteDatabase {
        return Room.databaseBuilder(
            this,
            NoteDatabase::class.java,
            "note_database"
        ).build()
    }
}
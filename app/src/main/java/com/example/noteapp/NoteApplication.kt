package com.example.noteapp

import android.app.Application
import androidx.room.Room
import com.example.noteapp.data.NoteDatabase

class NoteApplication : Application() {

    lateinit var database: NoteDatabase

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            NoteDatabase::class.java,
            "note_database"
        ).build()
    }
}
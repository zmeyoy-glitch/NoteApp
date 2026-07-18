package com.example.noteapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var database: NoteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize Room Database
        database = Room.databaseBuilder(
            application,
            NoteDatabase::class.java,
            "note_database"
        ).build()

        setContentView(R.layout.activity_main)

        Toast.makeText(this, "NoteApp initialized with Room DB", Toast.LENGTH_SHORT).show()
    }
}
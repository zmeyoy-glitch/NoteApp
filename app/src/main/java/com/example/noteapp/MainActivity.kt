package com.example.noteapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.launch
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var database: NoteDatabase
    private lateinit var noteDao: NoteDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize Room Database with singleton pattern
        database = Room.databaseBuilder(
            application,
            NoteDatabase::class.java,
            "note_database"
        ).build()

        noteDao = database.noteDao()

        setContentView(R.layout.activity_main)

        Toast.makeText(this, "NoteApp initialized with Room DB", Toast.LENGTH_SHORT).show()
        
        // Example: Load notes when activity starts
        loadNotes()
    }

    private fun loadNotes() {
        lifecycleScope.launch {
            noteDao.getAllNotes().collect { notes ->
                if (notes.isEmpty()) {
                    Toast.makeText(this@MainActivity, "No notes found", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "${notes.size} notes loaded", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Optional: Close database when activity is destroyed
        // database.close()
    }
}
package com.example.noteapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.noteapp.data.NoteUiModel
import com.example.noteapp.data.NoteViewModel
import com.example.noteapp.databinding.ActivityNoteDetailBinding

class NoteDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteDetailBinding
    private lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val noteId = intent.getLongExtra("noteId", 0L)
        loadNote(noteId)
    }

    private fun loadNote(noteId: Long) {
        viewModel = ViewModelProvider(this)[NoteViewModel::class.java]
        
        // Load the specific note (you'll need to implement this in your repository)
        val note = NoteUiModel(
            id = noteId,
            title = "Sample Title",
            content = "Sample Content"
        )

        binding.titleText.text = note.title
        binding.contentText.text = note.content
        
        // Setup toolbar with back button and delete action
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
package com.example.noteapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteapp.data.NoteViewModel
import com.example.noteapp.databinding.ActivityNoteBinding

class NoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteBinding
    private lateinit var viewModel: NoteViewModel
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        initViewModel()
        loadNotes()
    }

    private fun setupRecyclerView() {
        adapter = NoteAdapter(
            onNoteClick = { id ->
                // Navigate to note detail or edit screen
                val intent = Intent(this, NoteDetailActivity::class.java).apply {
                    putExtra("noteId", id)
                }
                startActivity(intent)
            },
            onDeleteClick = { id ->
                viewModel.deleteNote(NoteUiModel(id = id, title = "", content = ""))
            }
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[NoteViewModel::class.java]
        viewModel.init(NoteRepository(viewModel))
    }

    private fun loadNotes() {
        viewModel.loadNotes()
    }
}
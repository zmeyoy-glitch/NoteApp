package com.example.noteapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteapp.data.NoteRepository
import com.example.noteapp.data.NoteViewModel
import com.example.noteapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: NoteViewModel
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupViewModel()
    }

    private fun setupRecyclerView() {
        adapter = NoteAdapter(
            onItemClick = { id ->
                // Handle note click (e.g., open detail screen)
                viewModel.getNoteById(id)?.let { note ->
                    // TODO: Navigate to detail screen
                }
            },
            onItemDelete = { id ->
                // Handle delete action
                viewModel.getNoteById(id)?.let { note ->
                    viewModel.delete(note)
                }
            }
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun setupViewModel() {
        val repository = NoteRepository(NoteDatabase.getInstance(this))
        viewModel = ViewModelProvider(this, ViewModelFactory(repository))[NoteViewModel::class.java]

        viewModel.notes.collect { notes ->
            adapter.submitList(notes)
        }
    }
}
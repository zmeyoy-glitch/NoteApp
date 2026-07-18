package com.example.noteapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.data.NoteAdapter
import com.example.noteapp.data.NoteEntity
import com.example.noteapp.data.NoteRepository
import com.example.noteapp.data.NoteViewModel
import com.example.noteapp.data.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: NoteViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val repository = NoteRepository(NoteDatabase.getInstance(this).noteDao())
        viewModel = ViewModelProvider(this, ViewModelFactory(repository))[NoteViewModel::class.java]

        adapter = NoteAdapter(emptyList()) { note ->
            viewModel.delete(note)
        }

        recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        val notes = viewModel.notes.value
        adapter.submitList(notes)
    }
}
package com.example.noteapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.data.NoteDatabase
import com.example.noteapp.data.NoteEntity
import com.example.noteapp.data.NoteRepository
import com.example.noteapp.data.NoteViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: NoteViewModel
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val database = NoteDatabase.getInstance(applicationContext)
        val repository = NoteRepository(database.noteDao())
        viewModel = ViewModelProvider(this, ViewModelFactory(repository))[NoteViewModel::class.java]

        adapter = NoteAdapter()
        recyclerView.adapter = adapter

        viewModel.notes.observe(this) { notes ->
            adapter.submitList(notes)
        }
    }
}
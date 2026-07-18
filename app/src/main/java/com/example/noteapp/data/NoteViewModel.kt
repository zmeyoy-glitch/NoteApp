package com.example.noteapp.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NoteViewModel : ViewModel() {

    private val _notes = MutableStateFlow<List<NoteUiModel>>(emptyList())
    val notes: StateFlow<List<NoteUiModel>> = _notes.asStateFlow()

    private lateinit var repository: NoteRepository

    fun init(repository: NoteRepository) {
        this.repository = repository
    }

    fun loadNotes() {
        viewModelScope.launch {
            repository.allNotes.collect { notes ->
                _notes.value = notes.map { NoteUiModel.fromEntity(it) }
            }
        }
    }

    fun insertNote(note: NoteUiModel) {
        viewModelScope.launch {
            val entity = NoteEntity(
                id = 0, // Will be auto-generated
                title = note.title,
                content = note.content,
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            )
            repository.insert(entity)
        }
    }

    fun updateNote(note: NoteUiModel) {
        viewModelScope.launch {
            val entity = NoteEntity(
                id = note.id,
                title = note.title,
                content = note.content,
                createdAt = note.createdAt,
                updatedAt = System.currentTimeMillis()
            )
            repository.update(entity)
        }
    }

    fun deleteNote(note: NoteUiModel) {
        viewModelScope.launch {
            val entity = NoteEntity(
                id = note.id,
                title = "",
                content = "",
                createdAt = 0L,
                updatedAt = 0L
            )
            repository.delete(entity)
        }
    }

    fun deleteAllNotes() {
        viewModelScope.launch {
            repository.deleteAll()
            _notes.value = emptyList()
        }
    }
}
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
            repository.getAllNotes().collect { notes ->
                _notes.value = notes
            }
        }
    }

    suspend fun insertNote(note: NoteUiModel) {
        repository.insert(note)
        loadNotes()
    }

    suspend fun updateNote(note: NoteUiModel) {
        repository.update(note)
        loadNotes()
    }

    suspend fun deleteNote(note: NoteUiModel) {
        repository.delete(note.id)
        loadNotes()
    }

    fun getNotes(): Flow<List<NoteUiModel>> = _notes.asFlow()
}
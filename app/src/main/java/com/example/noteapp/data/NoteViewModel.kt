package com.example.noteapp.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class NoteUiModel(
    val id: Long,
    val title: String,
    val content: String,
    val createdAt: Long,
    val updatedAt: Long
)

class NoteViewModel : ViewModel() {

    private val _notes = MutableStateFlow<List<NoteUiModel>>(emptyList())
    val notes: StateFlow<List<NoteUiModel>> = _notes.asStateFlow()

    private val repository: NoteRepository by lazy {
        NoteRepository(NoteDatabase.getDatabase(viewModel.applicationContext).noteDao())
    }

    fun loadNotes() {
        viewModelScope.launch {
            repository.allNotes.collect { notes ->
                _notes.value = notes.map { noteEntity ->
                    NoteUiModel(
                        id = noteEntity.id,
                        title = noteEntity.title,
                        content = noteEntity.content,
                        createdAt = noteEntity.createdAt,
                        updatedAt = noteEntity.updatedAt
                    )
                }
            }
        }
    }

    fun insertNote(note: NoteUiModel) {
        viewModelScope.launch {
            val entity = NoteEntity(
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
            repository.delete(NoteEntity(id = note.id, title = "", content = "", createdAt = 0L, updatedAt = 0L))
        }
    }
}